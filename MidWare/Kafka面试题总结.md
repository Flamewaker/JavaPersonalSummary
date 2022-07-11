# Kafka 面试题总结

##### Kafka 如何保证 exactly once？ 追问、谈谈你对Kafka幂等性的理解？

**(1) Kafka 如何保证 exactly once？**

Exactly once（精确的⼀次）: 不会漏传输也不会重复传输，每个消息都传输⼀次⽽且仅仅被传输⼀次。

在0.11版本之后，Kafka 引入了幂等性机制（idempotent），配合 acks = -1 时的 at least once 语义，实现了 producer 到 broker 的 exactly once 语义。

idempotent （幂等性） + at least once （至少一次） = exactly once

使用时，只需将 enable.idempotence 属性设置为true，kafka 自动将 acks 属性设为-1。

**(2) Kafka幂等性**

Kafka幂等性指的 Producer 无论向 Broker 发送了多少条重复的消息，Broker 只会持久化一条，数据不丟不重。

但是这里的幂等性是有条件的：幂等性只解决了当前的会话且当前的分区的幂等性。跨分区、会话不能实现精准一次性投递写入。

> 幂等性实现原理：
>
> Kafka 引入了Producer ID（即PID）和 Sequence Number。每个新的 Producer 在初始化的时候会被分配一个唯一的 PID，该PID对用户完全透明而不会暴露给用户。对于每个 PID，该 Producer 发送数据的每个 <Topic, Partition> 都对应一个从0开始单调递增的Sequence Number。类似地，Broker 端也会为每个 <PID, Topic, Partition> 维护一个序号，并且每次 Commit 一条消息时将其对应序号递增。
>
> Producer 发送消息到同一个分区 partition 的时候，消息会附带一个 Sequence Number 序列号，Broker 会以 <PID, Patition, SeqNumber> 作为主键 key 进行缓存，当具有相同的主键key的消息进行提交的时候，Broker只会持久化一条。