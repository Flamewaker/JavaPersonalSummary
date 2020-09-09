# Spark的运行架构
## 1. 基本概念
* RDD：是弹性分布式数据集（Resilient Distributed Dataset）的简称，是分布式内存的一个抽象概念，提供了一种高度受限的共享内存模型；
* DAG：是Directed Acyclic Graph（有向无环图）的简称，反映RDD之间的依赖关系；
* Executor：是运行在工作节点（Worker Node）上的一个进程，负责运行任务，并为应用程序存储数据；
* 应用：用户编写的Spark应用程序；
* 任务：运行在Executor上的工作单元；
* 作业：一个作业包含多个RDD及作用于相应RDD上的各种操作；
* 阶段：是作业的基本调度单位，一个作业会分为多组任务，每组任务被称为“阶段”，或者也被称为“任务集”。

1) 当一个Spark应用被提交时，首先需要为这个应用构建起基本的运行环境，即由任务控制节点（Driver）创建一个SparkContext，由SparkContext负责和资源管理器（Cluster Manager）的通信以及进行资源的申请、任务的分配和监控等。SparkContext会向资源管理器注册并申请运行Executor的资源；

2) 资源管理器为Executor分配资源，并启动Executor进程，Executor运行情况将随着“心跳”发送到资源管理器上；

3) SparkContext根据RDD的依赖关系构建DAG图，DAG图提交给DAG调度器（DAGScheduler）进行解析，将DAG图分解成多个“阶段”（每个阶段都是一个任务集），并且计算出各个阶段之间的依赖关系，然后把一个个“任务集”提交给底层的任务调度器（TaskScheduler）进行处理；Executor向SparkContext申请任务，任务调度器将任务分发给Executor运行，同时，SparkContext将应用程序代码发放给Executor；

4) 任务在Executor上运行，把执行结果反馈给任务调度器，然后反馈给DAG调度器，运行完毕后写入数据并释放所有资源。

## 2. RDD概念
一个RDD就是一个分布式对象集合，本质上是一个只读的分区记录集合，每个RDD可以分成多个分区，每个分区就是一个数据集片段，并且一个RDD的不同分区可以被保存到集群中不同的节点上，从而可以在集群中的不同节点上进行并行计算。

RDD提供了一种高度受限的共享内存模型，即RDD是只读的记录分区的集合，不能直接修改，只能基于稳定的物理存储中的数据集来创建RDD，或者通过在其他RDD上执行确定的转换操作（如map、join和groupBy）而创建得到新的RDD。

RDD提供了一组丰富的操作以支持常见的数据运算，分为“行动”（Action）和“转换”（Transformation）两种类型，前者用于执行计算并指定输出的形式，后者指定RDD之间的相互依赖关系。

Spark用Scala语言实现了RDD的API，程序员可以通过调用API实现对RDD的各种操作。RDD典型的执行过程如下：
1. RDD读入外部数据源（或者内存中的集合）进行创建；
2. RDD经过一系列的“转换”操作，每一次都会产生不同的RDD，供给下一个“转换”使用；
3. 最后一个RDD经“行动”操作进行处理，并输出到外部数据源（或者变成Scala集合或标量）。

---

1. 创建这个Spark程序的执行上下文，即创建SparkContext对象；
2. 从外部数据源（即HDFS文件）中读取数据创建fileRDD对象；
3. 构建起fileRDD和filterRDD之间的依赖关系，形成DAG图，这时候并没有发生真正的计算，只是记录转换的轨迹；
4. 行动类型的操作，触发真正的计算，开始实际执行从fileRDD到filterRDD的转换操作，并把结果持久化到内存中，最后计算出filterRDD中包含的元素个数。

RDD采用了惰性调用，即在RDD的执行过程中, 真正的计算发生在RDD的“行动”操作，对于“行动”之前的所有“转换”操作，Spark只是记录下“转换”操作应用的一些基础数据集以及RDD生成的轨迹，即相互之间的依赖关系，而不会触发真正的计算。

一系列处理称为一个“血缘关系（Lineage）”，即DAG拓扑排序的结果。采用惰性调用，通过血缘关系连接起来的一系列RDD操作就可以实现管道化（pipeline），避免了多次转换操作之间数据同步的等待，而且不用担心有过多的中间数据，因为这些具有血缘关系的操作都管道化了，一个操作得到的结果不需要保存为中间数据，而是直接管道式地流入到下一个操作进行处理。

## 3. RDD之间的依赖关系
RDD中不同的操作会使得不同RDD中的分区会产生不同的依赖。RDD中的依赖关系分为窄依赖（Narrow Dependency）与宽依赖（Wide Dependency）

窄依赖表现为一个父RDD的分区对应于一个子RDD的分区，或多个父RDD的分区对应于一个子RDD的分区；

宽依赖则表现为存在一个父RDD的一个分区对应一个子RDD的多个分区。窄依赖典型的操作包括map、filter、union等，宽依赖典型的操作包括groupByKey、sortByKey等。

## 4. 阶段的划分

Spark通过分析各个RDD的依赖关系生成了DAG，再通过分析各个RDD中的分区之间的依赖关系来决定如何划分阶段，具体划分方法是：在DAG中进行反向解析，遇到宽依赖就断开，遇到窄依赖就把当前的RDD加入到当前的阶段中；将窄依赖尽量划分在同一个阶段中，可以实现流水线计算。

## 5. RDD运行过程
通过上述对RDD概念、依赖关系和阶段划分的介绍，结合之前介绍的Spark运行基本流程，这里再总结一下RDD在Spark架构中的运行过程：
（1）创建RDD对象；
（2）SparkContext负责计算RDD之间的依赖关系，构建DAG；
（3）DAGScheduler负责把DAG图分解成多个阶段，每个阶段中包含了多个任务，每个任务会被任务调度器分发给各个工作节点（Worker Node）上的Executor去执行。

## 6. Spark的Shuffle
（1）MapReduce的Shuffle操作分为，Map端的Shuffle操作和Reduce端的Shuffle操作。

1. Map端的Shuffle操作首先将Map的输出结果写入缓存，当缓存满的时候，启动缓存的溢写操作，把缓存的数据写入磁盘文件，并清空缓存。缓存的溢写操作首先把缓存中的数据进行分区，然后再进行排序合并。每次溢写操作都会都会生成一个文件，当所有的Map操作结束后，会将所有的文件进行合并。通知Reduce任务来领取自己需要处理的分区数据。
2. Reduce端的Shuffle操作从不同的Map机器领取属于自己的那部分数据，然后对数据进行归并，最后进行处理。

（2）Spark的Shuffle操作，每一个Map任务会根据Reduce任务数量创建出相应的桶，Map任务会将产生的结果根据设置的分区填充到每个桶中。Spark采用将多个桶写入一个文件的方式，并为每个文件设立相应的索引文件，索引文件存储了数据文件中的数据的分区信息。Reduce任务根据索引的文件，来获取属于自己的那个分区的数据。

就是最开始的时候使用的是 Hash Based Shuffle， 这时候每一个Mapper会根据Reducer的数量创建出相应的bucket，bucket的数量是M R ，其中M是Map的个数，R是Reduce的个数。这样会产生大量的小文件，对文件系统压力很大，而且也不利于IO吞吐量。后面忍不了了就做了优化，把在同一core上运行的多个Mapper 输出的合并到同一个文件，这样文件数目就变成了 cores R 个了。

后面就引入了 Sort Based Shuffle， map端的任务会按照Partition id以及key对记录进行排序。同时将全部结果写到一个数据文件中，同时生成一个索引文件， 再后面就就引入了 Tungsten-Sort Based Shuffle， 这个是直接使用堆外内存和新的内存管理模型，节省了内存空间和大量的gc， 是为了提升性能。

现在2.1 分为三种writer， 分为 BypassMergeSortShuffleWriter， SortShuffleWriter 和 UnsafeShuffleWriter。本文中只描述这三种 writer 的实现细节， Hash Based Shuffle 已经退出历史舞台了，我就不讲了。

* BypassMergeSortShuffleWriter和Hash Shuffle中的HashShuffleWriter实现基本一致， 唯一的区别在于，map端的多个输出文件会被汇总为一个文件。 所有分区的数据会合并为同一个文件，会生成一个索引文件，是为了索引到每个分区的起始地址，可以随机 access 某个partition的所有数据。

* SortShuffleWriter 实现细节

SortShuffleWriter 中的处理步骤就是

使用 PartitionedAppendOnlyMap 或者 PartitionedPairBuffer 在内存中进行排序，  排序的 K 是（partitionId， hash（key）） 这样一个元组。

如果超过内存 limit， 我 spill 到一个文件中，这个文件中元素也是有序的，首先是按照 partitionId的排序，如果 partitionId 相同， 再根据 hash（key）进行比较排序

如果需要输出全局有序的文件的时候，就需要对之前所有的输出文件 和 当前内存中的数据结构中的数据进行  merge sort， 进行全局排序

* UnsafeShuffleWriter 里面维护着一个 ShuffleExternalSorter， 用来做外部排序，  外部排序就是要先部分排序数据并把数据输出到磁盘，然后最后再进行merge 全局排序， 既然这里也是外部排序，跟 SortShuffleWriter 有什么区别呢， 这里只根据 record 的 partition id 先在内存 ShuffleInMemorySorter 中进行排序， 排好序的数据经过序列化压缩输出到换一个临时文件的一段，并且记录每个分区段的seek位置，方便后续可以单独读取每个分区的数据，读取流经过解压反序列化，就可以正常读取了。
