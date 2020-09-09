# Flink的DAG图分析

https://blog.csdn.net/weixin_39657860/article/details/96756705

## 一.DAG图的总体架构： 

  Flink框架引擎把执行计划抽象为四个层次的数据结构，分别是API层、静态topology、JobGraph、ExecutionGraph等。

![DAG](https://img-blog.csdnimg.cn/20190721203031702.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTY1Nzg2MA==,size_16,color_FFFFFF,t_70)、

在JobManager端，会接收到Client提交的JobGraph形式的Flink Job，JobManager会将一个JobGraph转换映射为一个ExecutionGraph，ExecutionGraph是JobGraph的并行表示，也就是实际JobManager调度一个Job在TaskManager上运行的逻辑视图。

Flink 中的执行图可以分成四层：StreamGraph -> JobGraph -> ExecutionGraph -> 物理执行图

* StreamGraph：是根据用户通过Stream API编写的代码生成的最初的图。用来表示程序的拓扑结构。

* JobGraph：StreamGraph经过优化后生成了JobGraph，提交给JobManager 的数据结构。主要的优化为，将多个符合条件的节点chain在一起作为一个节点

* ExecutionGraph：JobManager 根据JobGraph生成ExecutionGraph。ExecutionGraph是JobGraph的并行化版本，是调度层最核心的数据结构。根据并行度进行优化。

* 物理执行图：JobManager根据ExecutionGraph对Job进行调度后，在各个TaskManager 上部署Task 后形成的“图”，并不是一个具体的数据结构。

## 二. 流作业DAG图分析

### API层

    用户通过StreamApI或是BatchAPI描述Job的逻辑,并通过调用链把作业逻辑操作串联起来，对于用户而言，业务抽象的是一个一个的有特定功能的Operator（操作算子），通过Operator的组合来来描述一个作业的对数据的处理过程。

* 对于流作业：
  用户程序通过调用StreamExecutionEnvironment.execute触发StreamGraphGenerator生成StreamGraph。
* 对于批作业：
  用户程序通过OptimizerPlanEnvironment.execute触发Optimizer生成OptimizerPlan


### 静态Graph层

* StreamGraph

   首先StreamGraph的核心是一个StreamNode的拓扑图，StreamNode之间是通过StreamEdge连接。

   该拓扑图是通过翻译transformations的链表而来，用户的userFunction最终被flink框架封装成StreamTransformation的调用链，然后再翻译成StreamGraph。普通的Operator会通过图生成器（ StreamGraphGenerator）的transform方法来生成对于的StreamNode，而实现分流功能的Select操作以及实现分区的分区选择器则被翻译成虚拟节点并加入到StreamGraph中。

下面是的transforms转换成StreamNode的核心代码：

```java
Collection<Integer> transformedIds;
		if (transform instanceof OneInputTransformation<?, ?>) {
			transformedIds = transformOneInputTransform((OneInputTransformation<?, ?>) transform);
		} else if (transform instanceof TwoInputTransformation<?, ?, ?>) {
			transformedIds = transformTwoInputTransform((TwoInputTransformation<?, ?, ?>) transform);
		} else if (transform instanceof SourceTransformation<?>) {
			transformedIds = transformSource((SourceTransformation<?>) transform);
		} else if (transform instanceof SinkTransformation<?>) {
			transformedIds = transformSink((SinkTransformation<?>) transform);
		} else if (transform instanceof UnionTransformation<?>) {
			transformedIds = transformUnion((UnionTransformation<?>) transform);
		} else if (transform instanceof SplitTransformation<?>) {
			transformedIds = transformSplit((SplitTransformation<?>) transform);
		} else if (transform instanceof SelectTransformation<?>) {
			transformedIds = transformSelect((SelectTransformation<?>) transform);
		} else if (transform instanceof FeedbackTransformation<?>) {
			transformedIds = transformFeedback((FeedbackTransformation<?>) transform);
		} else if (transform instanceof CoFeedbackTransformation<?>) {
			transformedIds = transformCoFeedback((CoFeedbackTransformation<?>) transform);
		} else if (transform instanceof PartitionTransformation<?>) {
			transformedIds = transformPartition((PartitionTransformation<?>) transform);
		} else if (transform instanceof SideOutputTransformation<?>) {
			transformedIds = transformSideOutput((SideOutputTransformation<?>) transform);
		} else {
			throw new IllegalStateException("Unknown transformation: " + transform);
		}
```

 从上面的代码中可以看到框架根据Transform的类型调用不同transformX函数进行转换。

1）其中OneInput、TwoInput、Source、sink的Transform大体相同就是封装StreamNode然后加入到Graph中，所不同的是对Edge的处理不同。

2）split和select的Transform成对使用，一起配合完成分流的功能。transformsSplit会根据split.getOutputSelector()来addOutputSelector，而tarnsformsSelect则会根据selectdNames生产相应的VirtualNode

3） transformPartition和transformSideOutput则是生成VirtualNode并分别把OutputTag、StreamPartitioner加入对应的列表中。

* StreamNode：

  StreamNode的核心组件是：operator、inEdges、outEdges

  （1）Operator：Operator则是StreamOperator的实例，封装了UserDefineFunction和算子本身state，以及提供管理自定义state 、Channel的接口。

  （2）inEdges和outEdges：inEdges和outEdges是StreamEdge的实列用于把Connector各个StreamNode，构成整个执行拓扑图。

  （3）StreamEdge：StreamEdge包括source和target 分别用于指向源和目标的StreamNode,此外SteamEdge还包括select的Names列表、分区选择器outputPartitioner，以及outputTag。
  

### JobGraph层

* 什么是jobGraph?

  jobGraph是唯一能被Flink引擎所识别的表述作业的数据结构，也正是这一共同的抽象为Flink批量统一奠定了基础。相比于StreamGraph和OptimizedPlan这些静态的拓扑图JobGraph加入了中间结果集（IntermediateDataSet）使其赋予了动态的特性，就像流水一样源源不断流淌。

* JobGraph的组成：
      JobGraph是由顶点（JobVertex）、作业边（JobEdge）以及中间结果集（IntermediateDataSet）三个基本的元素组成。其中JobEdge和IntermediateDateSet都是多个Vertex之间的连接的纽带，分别代表顶点的input和result。


（1）一个JobVertex关联着若干个JobEdge作为输入端以及若干个IntermediateDataSet作为其产生的结果集合。

（2）	一个JobEdge关联着一个IntermeidateDataSet作为其数据源和一个JobVertex作为其消费者

（3）	一个IntermediateDataSet关联着一个Vertex作为其生产者，和若干的JobEdge作为其消费者

NOTE：这就如同一个抽水系统，Vertex是抽水泵，JobEdge是水管，ItermediateDateSet就是中间的蓄水池。


* JobVertex

  JobVertex是JobGraph中最重要的元素之一。实际上JobGraph只是维护了一个JobVertex类型的Map数据结构，JobEdge和中间结果集IntermediateDataSet都Vertex的成员变量，通过各个顶点Vertex之间的关系形成一个完整的动态Topology图。 JobVertex的组成是：id,inputList,results等。

**流作业的JobGraph生成过程：**

    流作业中JobGraph的生成是由StreamingJobGraphGenerator的createJobGraph完成的，该函数的调用入口是StramGraph的getJobGraph。

jobGraph的生成过程大体分为三个阶段：

**第一阶段：初始化阶段：**

   主要是实列化JobGraph对象并初始化一些实列变量。然后通过StreamGraphHasher生产一批hash值用于标识jobGraph中的顶点JobVertex，如果DAG图没有变化这批hash值多次生成的结果是不变的，这也为后续failover Recover提供了可行性。

   Hash的生成是通过visitor以广度的方式遍历StreamGrah的StreamNode来实现。

Hash的计算方式：

1.如果用户指定了TransformationUID则使用该UID生产对应的hash值

2.否则根据Job中的综合属性计算。

对于第二种方式的计算因素包括下面三种：

（1）	StreamNode中的序列化序号

（2）	Chain在一起的输出Node的序号

（3）	输入Node的hash值

因此改变上面中任务一个属性都会造成JobVertexID的变化

**第二阶段：创建并链接 JobVertex：**

   为了更加高效的执行，Flink对DAG图进行在调度上做里一定的优化，即算子链接（operator chain）,也就是把StreamGraph中的若干个StreamNode chain在一起共同组成Flink中的调度任务Task，在Job的执行时候，这些调度Task会按照并发度分割成多个subTask分布到执行线程中执行。在旧版本中每个subTask分配一个slot执行，在之后加入了sharingSlot方式，一个slot会执行多个subTask任务。

优化的的目的：
1) 减少Job调度的Task数量，从而减少线程之间的切换的开销提高CPU的利用率。
2).减少job中不同node之间数据交换以及不同JVM之间的网络开销提高数据交换的速度

最终达到减少延时的同时提高了系统的吞吐。

**Chain的实现**：

JobVertex的chain通过setChaining的调用触发，遍历所有的源头StreamNode逐条Chain的调用createChain完成链路的设置，每个source到sink的StreamNode组成一条单独的chain。
对于单条chain的Connection，则是通过遍历StreamNodes图采用递归的调用的方式实现，最后从sink节点开始开始往前做下面操作：
1）	设置当前节点的ChainName，Min和preferred Resources
2）	如果是当前节点非Vertex的header节点则单纯的创建StreamConfig对象并设置相关的属性,其属性包括ID 、In/OutTypeSerializer、Operator、Chain/nonChain Outputs、CheckPointConfig、StateBackend等。
3）	如果当前Node是Vertex的header，则在生产和设置StreamConfig之前会创建JobVertex对象初始化后加入到JobStream中，最后通过调用connect函数，创建作业边（JobEdge）和中间结果集（IntermediateDataSet）把其他的JobVertex关联起来。

**Chain的算法：**
如果要把一个StreamNode的OutputEdge链接到一个Vertex中需要同时满足下面的条件：
1．	当前节点的OutputEdge的上、下游节点的Operator不能为空
2．	当前Node的下游节点的输入边除了当前的出边外没有其他入边（即是该节点的inEdges的长度是1）。
3．	上下游节点是在同一个SharingGroup组中或为空
4．	上下游节点的并发度要相同
5．	下游Operator的ChainingStrategy是ALWAYS，而上游（当前节点）的Operator的ChainingStrategy是HEAD或ALWAYS。
6．	本条JobEdge的Partitioner是Forward。
7．	作业的配置允许Chaining

**第三个阶段：设置JobGraph中其他属性**
（1） 设置JobGraph的物理边

所谓的物理边其实就是真正用于链接不同JobVertex之间的Edge，也就是前面阐述的JobEdge。在这里调用setPhysicalEdges只是单纯的把第二阶段调Connect函数时的产生的JobEdge所对应的StreamEdge组织成Map数据结构并把引用保存到StreamingJobGraphGenerator，JobEdge的生成和结果集IntermediaDateSet都是在第二阶段完成。

（2）设置槽共享组（SlotSharingGroup）以及同位组（CoLocationGroup）。其中同位组主要用于迭代算子的执行。
（3）设置Job的Checkpoint配置。

### ExecutionGraph层

ExecutionGraph是作业在调度阶段的数据结构,它是JobGraph并行化之后的视图是调度层最核心的数据结构。

**ExecutionGraph的核心组：**

类似的ExecutionGraph同样是由ExecutionJobVertex、IntermediateResult、ExecutionEdge等三个核心组件构成，分别对应着JobGraph中的JobVertex、IntermediateDataSet以及JobEdge。和JobGraph有所不同的是ExecutionGraph根据并发度进一步把ExecutionGraph划分为等同于并发度个数量的多个ExecutionVertex、IntermediateResultPartition及ExecutionEdge。
转换示例图如下：

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20190722005435637.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTY1Nzg2MA==,size_16,color_FFFFFF,t_70)

**ExecutionGraph的生成**

ExecutionGraph的生成器是ExecutionGraphBuilder，在Client提交job之后，JobManager会通过生成JobMaster对象的同时触发ExecutionGraphBuilder的buildGraph函数进而生成ExecutionGraph。

生成的过程很简单就是根据遍历JobGraph中的JobVertex根据其属性设置ExecutionGraph中的属性信息，然后根据parallelism生成相应的ExecutionVertex。

至此FLink流作业的执行计划基本完成，最后就是执行计划的物理调度了这里就不在赘述。