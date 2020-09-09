# Flink的运行架构

1. 作业管理器（JobManager）
2. 任务管理器（TaskManager）
3. 资源管理器（ResourceManager）
4. 分发器（Dispatcher）

## 1. 作业管理器（JobManager）
控制一个应用程序执行的主进程。

* JobManager 会先接收到要执行的应用程序，这个应用程序会包括：作业图（JobGraph）、逻辑数据流图（logical dataflow graph）和打包了所有的类、库和其它资源的JAR包。

* JobManager 会把JobGraph转换成一个物理层面的数据流图，这个图被叫做“执行图”（ExecutionGraph），包含了所有可以并发执行的任务。

* JobManager 会向资源管理器（ResourceManager）请求执行任务必要的资源，也就是任务管理器（TaskManager）上的插槽（slot）。一旦它获取到了足够的资源，就会将执行图分发到真正运行它们的TaskManager上。而在运行过程中，JobManager会负责所有需要中央协调的操作，比如说检查点（checkpoints）的协调。

## 2. 任务管理器（TaskManager）

Flink中的工作进程。通常在Flink中会有多个TaskManager运行，每一个TaskManager都包含了一定数量的插槽（slots）。插槽的数量限制了TaskManager能够执行的任务数量。

* 启动之后，TaskManager会向资源管理器注册它的插槽；收到资源管理器的指令后，TaskManager就会将一个或者多个插槽提供给JobManager调用。JobManager就可以向插槽分配任务（tasks）来执行了。

* 在执行过程中，一个TaskManager可以跟其它运行同一应用程序的TaskManager交换数据。


## 3. 资源管理器（ResourceManager）

主要负责管理任务管理器（TaskManager）的插槽（slot），TaskManger 插槽是Flink中定义的处理资源单元。

* Flink为不同的环境和资源管理工具提供了不同资源管理器，比如YARN、Mesos、K8s，以及standalone部署。

* 当JobManager申请插槽资源时，ResourceManager会将有空闲插槽的TaskManager分配给JobManager。如果ResourceManager没有足够的插槽来满足JobManager的请求，它还可以向资源提供平台发起会话，以提供启动TaskManager进程的容器。

## 4. 分发器（Dispatcher）

它为应用提交提供了REST接口。

* 当一个应用被提交执行时，分发器就会启动并将应用移交给一个JobManager。

* Dispatcher也会启动一个Web UI，用来方便地展示和监控作业执行的信息。

* Dispatcher在架构中可能并不是必需的，这取决于应用提交运行的方式。

![任务提交流程](\pictures\任务提交流程.jpg)

## 5. TaskManager和Slots
* Flink 中每一个TaskManager都是一个JVM进程，它可能会在独立的线程上执行一个或多个subtask（子任务）。

* 为了控制一个TaskManager能接收多少个task，TaskManager通过task slot来进行控制（一个TaskManager至少有一个slot）

* 默认情况下，Flink 允许子任务共享slot，即使它们是不同任务的子任务。这样的结果是，一个slot可以保存作业的整个管道。

* Task Slot是静态的概念，是指TaskManager具有的并发执行能力。


![TaskManger和Slots](\pictures\TaskManger和Slots.jpg)

## 6. 程序和数据流（Dataflow）
所有的Flink程序都是由三部分组成的：Source、Transformation和Sink。

Source负责读取数据源，Transformation利用各种算子进行处理加工，Sink负责输出。

* 在运行时，Flink上运行的程序会被映射成“逻辑数据流”（dataflows），它包含了这三部分。

* 每一个dataflow以一个或多个sources开始以一个或多个sinks结束。dataflow类似于任意的有向无环图（DAG）。

* 在大部分情况下，程序中的转换运算（transformations）跟dataflow中的算子（operator）是一一对应的关系。

## 7. 执行图（ExecutionGraph）

在JobManager端，会接收到Client提交的JobGraph形式的Flink Job，JobManager会将一个JobGraph转换映射为一个ExecutionGraph，ExecutionGraph是JobGraph的并行表示，也就是实际JobManager调度一个Job在TaskManager上运行的逻辑视图。

Flink 中的执行图可以分成四层：StreamGraph -> JobGraph -> ExecutionGraph -> 物理执行图

* StreamGraph：是根据用户通过Stream API编写的代码生成的最初的图。用来表示程序的拓扑结构。

* JobGraph：StreamGraph经过优化后生成了JobGraph，提交给JobManager 的数据结构。主要的优化为，将多个符合条件的节点chain在一起作为一个节点

* ExecutionGraph：JobManager 根据JobGraph生成ExecutionGraph。ExecutionGraph是JobGraph的并行化版本，是调度层最核心的数据结构。根据并行度进行优化。

* 物理执行图：JobManager根据ExecutionGraph对Job进行调度后，在各个TaskManager 上部署Task 后形成的“图”，并不是一个具体的数据结构。

## 8. 并行度（Parallelism）

Flink使用并行度来定义某个算子被切分为多少个算子子任务。当实际运行时，逻辑视图中的算子会被并行切分为一到多个算子子任务，每个算子子任务处理一部分数据。

* 一个特定算子的子任务（subtask）的个数被称之为其并行度（parallelism）。一般情况下。一个stream的并行度，可以认为就是其所有算子中最大的并行度。

一个程序中，不同的算子可能具有不同的并行度

* 算子之间传输数据的形式可以是one-to-one的模式也可以是redistributing 的模式，具体是哪一种形式，取决于算子的种类

* One-to-one：**上游Subtask和下游Subtask保持了数据分区特性和分区内元素处理的有序性**。比如从 Source[1] 到 map()[1]，它保持了Source的分区特性和分区内元素处理的有序性，也就是说 map()[1] 的 Subtask 看到数据流中记录的顺序，与Source[1] 中看到的记录顺序是一致的。map、fliter、flatMap等算子都是one-to-one的对应关系。

* Redistributing：**模式改变了输入数据流的分区，上游的Subtask向下游的多个不同的 Subtask发送数据**。例如，keyBy基于hashCode重分区、而broadcast和rebalance会随机重新分区，这些算子都会引起redistribute过程，而redistribute 过程就类似于Spark 中的shuffle过程。

**任务链（Operator Chains）**

Flink 采用了一种称为任务链的优化技术，可以在特定条件下减少本地通信的开销。为了满足任务链的要求，必须将两个或多个算子设为相同的并行度，并通过本地转发（local forward）的方式进行连接。

Flink 分布式执行环境中，会将多个 Operator Subtask 串起来组成一个 Operator Chain，实际上就是一个执行链，每个执行链会在 TaskManager 上一个独立的线程中执行。

* 相同并行度的one-to-one操作，Flink 这样相连的算子链接在一起形成一个task，原来的算子成为里面的subtask。

* 并行度相同、并且是one-to-one操作，两个条件缺一不可

## 9. 数据重分布

默认情况下，数据是自动分配到多个实例上的。有的时候，我们需要手动对数据在多个实例上进行分配，例如，我们知道某个实例上的数据过多，其他实例上的数据稀疏，产生了数据倾斜，这时我们需要将数据均匀分布到各个实例上，以避免部分实例负载过重。数据倾斜问题会导致整个作业的计算时间过长或者内存不足等问题。

下文涉及到的各个数据重分布算子的输入是DataStream，输出也是DataStream。keyBy也有对数据进行分组和数据重分布的功能，但keyBy输出的是KeyedStream。

**shuffle**
shuffle基于正态分布，将数据随机分配到下游各算子实例上。

**rebalance与rescale**
rebalance使用Round-ribon思想将数据均匀分配到各实例上。Round-ribon是负载均衡领域经常使用的均匀分配的方法，上游的数据会轮询式地分配到下游的所有的实例上。

rescale与rebalance很像，也是将数据均匀分布到各下游各实例上，但它的传输开销更小，因为rescale并不是将每个数据轮询地发送给下游每个实例，而是就近发送给下游实例。

**broadcast**
英文单词"broadcast"翻译过来为广播，在Flink里，数据会被复制并广播发送给下游的所有实例上。

**global**
global会所有数据发送给下游算子的第一个实例上，使用这个算子时要小心，以免造成严重的性能问题。

**partitionCustom**
我们也可以使用partitionCustom来自定义数据重分布逻辑。partitionCustom有两个参数：第一个参数是自定义的Partitioner，我们需要重写里面的partition函数；第二个参数是对数据流哪个字段使用partiton逻辑。partition函数的返回一个整数，表示该元素将被路由到下游第几个实例。

## 10. 时间
处理 Stream 中的记录时，记录中通常会包含各种典型的时间字段：

Event Time：表示事件创建时间

Ingestion Time：表示事件进入到 Flink Dataflow 的时间

Processing Time：表示某个 Operator 对事件进行处理的本地系统时间

**Flink 使用 WaterMark 衡量时间的时间**，WaterMark 携带时间戳 t，并被插入到 stream 中。

WaterMark 的含义是所有时间 t’< t 的事件都已经发生。

**针对乱序的的流，WaterMark 至关重要，这样可以允许一些事件到达延迟**，而不至于过于影响 window 窗口的计算。

并行数据流中，当 Operator 有多个输入流时，Operator 的 event time 以最小流 event time 为准。

 

