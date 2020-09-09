# monitor的概念

monitor 的重要特点是，同一个时刻，只有一个 进程/线程能进入 monitor 中定义的临界区，这使得 monitor 能够达到互斥的效果。但仅仅有互斥的作用是不够的，无法进入monitor 临界区的进程/线程，它们应该被阻塞，并且在必要的时候会被唤醒。显然，monitor 作为一个同步工具，也应该提供这样的管理进程/线程状态的机制。想想我们为什么觉得 semaphore 和 mutex 在编程上容易出错，因为我们需要去亲自操作变量以及对 进程/线程 进行阻塞和唤醒。monitor这个机制之所以被称为“更高级的原语”，那么它就不可避免地需要对外屏蔽掉这些机制，并且在内部实现这些机制，使得使用 monitor 的人看到的是一个简洁易用的接口。

# monitor 基本元素

monitor 机制需要几个元素来配合，分别是：

1. 临界区
2. monitor 对象及锁
3. 条件变量以及定义在 monitor 对象上的 wait，signal 操作。

使用 monitor 机制的目的主要是为了互斥进入临界区，为了做到能够阻塞无法进入临界区的 进程/线程，还需要一个 monitor object 来协助，这个 monitor object 内部会有相应的数据结构，例如列表，来保存被阻塞的线程；同时由于 monitor 机制本质上是基于 mutex 这种基本原语的，所以 monitor object 还必须维护一个基于 mutex 的锁。

 此外，为了在适当的时候能够阻塞和唤醒 进程/线程，还需要引入一个条件变量，这个条件变量用来决定什么时候是“适当的时候”，这个条件可以来自程序代码的逻辑，也可以是在 monitor object 的内部，总而言之，程序员对条件变量的定义有很大的自主性。不过，由于 monitor object 内部采用了数据结构来保存被阻塞的队列，因此它也必须对外提供两个 API 来让线程进入阻塞状态以及之后被唤醒，分别是 wait 和 notify。

# Java 语言对 monitor 的支持

monitor 是操作系统提出来的一种高级原语，但其具体的实现模式，不同的编程语言都有可能不一样。以下以 Java 的 monitor 为例子，来讲解 monitor 在 Java 中的实现方式。

## 临界区的圈定

在 Java 中，可以采用 synchronized 关键字来修饰实例方法、类方法以及代码块，如下所示：



```java
/**
 * @author beanlam
 * @version 1.0
 * @date 2018/9/12
 */
public class Monitor {

    private Object ANOTHER_LOCK = new Object();

    private synchronized void fun1() {
    }

    public static synchronized void fun2() {
    }

    public void fun3() {
        synchronized (this) {
        }
    }

    public void fun4() {
        synchronized (ANOTHER_LOCK) {
        }
    }
}
```

被 synchronized 关键字修饰的方法、代码块，就是 monitor 机制的临界区。

## monitor object

可以发现，上述的 synchronized 关键字在使用的时候，往往需要指定一个对象与之关联，例如 synchronized(this)，或者 synchronized(ANOTHER_LOCK)，synchronized 如果修饰的是实例方法，那么其关联的对象实际上是 this，如果修饰的是类方法，那么其关联的对象是 this.class。总之，**synchronzied 需要关联一个对象，而这个对象就是 monitor object**。

 **monitor 的机制中，monitor object 充当着维护 mutex以及定义 wait/signal API 来管理线程的阻塞和唤醒的角色。**

 Java 语言中的 java.lang.Object 类，便是满足这个要求的对象，**任何一个 Java 对象都可以作为 monitor 机制的 monitor object。**

 Java 对象存储在内存中，分别分为三个部分，即**对象头、实例数据和对齐填充，而在其对象头中，保存了锁标识**；

同时，java.lang.Object 类定义了 wait()，notify()，notifyAll() 方法，这些方法的具体实现，依赖于一个叫 ObjectMonitor 模式的实现，这是 JVM 内部基于 C++ 实现的一套机制，基本原理如下所示：

![img](https:////upload-images.jianshu.io/upload_images/165290-aec815b4adf84922.png?imageMogr2/auto-orient/strip|imageView2/2/w/500/format/webp)

**当一个线程需要获取 Object 的锁时，会被放入 EntrySet 中进行等待，如果该线程获取到了锁，成为当前锁的 Owner。如果根据程序逻辑，一个已经获得了锁的线程缺少某些外部条件，而无法继续进行下去（例如生产者发现队列已满或者消费者发现队列为空），那么该线程可以通过调用 wait 方法将锁释放，进入 WaitSet 中阻塞进行等待，其它线程在这个时候有机会获得锁，去干其它的事情，从而使得之前不成立的外部条件成立，这样先前被阻塞的线程就可以重新进入 EntrySet 去竞争锁。这个外部条件在 monitor 机制中称为条件变量。**



Java Monitor 实现

（1） 每个java对象的对象头中，都有锁标识。

（2）java.lang.Object 类定义了 wait()，notify()，notifyAll() 方法。 这些都是 native方法，底层是C++来实现的。 这些方法的具体实现，依赖一个叫做ObjectMonitor模式实现，这是JVM内部C++实现的一套机制。

（3）ObjectMonitor模式

当一个线程想竞争到对象锁的时，第一步先被放入到等待队列中。如果这个线程获取到锁，称为对象的锁的拥有者，或缺失每个条件，或每个条件随着时间流逝，不满足的时候，该线程就会wait来释放锁。让给等待队列中的线程获取该锁的机会。这种机制，相对于是条件变量，或互斥变量。

（4）notify 方法： 解除阻塞，从阻塞中随机选择进行交接。

（5）notifyAll 方法： 会把所有被wait阻塞的线程，全部激活解除阻塞。最终也只有一个线程可以获取到锁。

（6）wait 方法： 进入阻塞