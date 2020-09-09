# ReentrantLock的底层实现机制

推荐阅读（https://www.cnblogs.com/takumicx/p/9402021.html）

ReentrantLock的底层实现机制是AQS(Abstract Queued Synchronizer 抽象队列同步器)。AQS没有锁之类的概念，它有个state变量，是个int类型，为了好理解，可以把state当成锁，AQS围绕state提供两种基本操作“获取”和“释放”，有条双向队列存放阻塞的等待线程。AQS的功能可以分为独占和共享，ReentrantLock实现了独占功能（每次只能有一个线程能持有锁）。

在ReentrantLock类中，有一个内部类Sync，它继承了AQS，但是将lock()方法定义为抽象方法，由子类负责实现（采用的是模板方法的设计模式）。

```
abstract void lock();
```

Sync分为公平锁和非公平锁，所以又有FairSync和NonfairSync继承Sync。

```java
/**
 * Creates an instance of {@code ReentrantLock} with the
 * given fairness policy.
 *
 * @param fair {@code true} if this lock should use a fair ordering policy
 */
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

ReentrantLock的默认构造实现是非公平锁，也就是线程获取锁的顺序和调用lock的顺序无关。所有线程同时去竞争锁，线程发出请求后立即尝试获取锁，如果有可用的则直接获取锁，失败才进入等待队列。



锁的获取过程：

1. 通过cas操作来修改state状态，表示争抢锁的操作，如果能够获取到锁，设置当前获得锁状态的线程。compareAndSetState(0, 1)

2. 如果没有获取到锁，尝试去获取锁。acquire(1)。

   （1）通过tryAcquire尝试获取独占锁，如果成功返回true，失败返回false。如果是同一个线程来获得锁，则直接增加重入次数，并返回true。

   （2）如果tryAcquire失败，则会通过addWaiter方法将当前线程封装成Node，添加到AQS队列尾部

   （3）acquireQueued，将Node作为参数，通过自旋去尝试获取锁。（如果前驱为head才有资格进行锁的抢夺。）

   （4）如果获取锁失败，则挂起线程。

锁的释放过程：

1. 释放锁。
2. 如果锁能够被其他线程获取，唤醒后继节点中的线程。一般情况下只要唤醒后继结点的线程就行了，但是后继结点可能已经取消等待，所以从队列尾部往前回溯，找到离头结点最近的正常结点，并唤醒其线程。

在获得同步锁时，同步器维护一个同步队列，获取状态失败的线程都会被加入到队列中并在队列中进行自旋；移出队列（或停止自旋）的条件是前驱节点为头节点且成功获取了同步状态。在释放同步状态时，同步器调用tryRelease(int arg)方法释放同步状态，然后唤醒头节点的后继节点。

## 1. Lock接口

Lock接口，是对控制并发的工具的抽象。它比使用synchronized关键词更灵活，并且能够支持条件变量。它是一种控制并发的工具，一般来说，它控制对某种共享资源的独占。也就是说，同一时间内只有一个线程可以获取这个锁并占用资源。其他线程想要获取锁，必须等待这个线程释放锁。在Java实现中的ReentrantLock就是这样的锁。另外一种锁，它可以允许多个线程读取资源，但是只能允许一个线程写入资源，ReadWriteLock就是这样一种特殊的锁，简称读写锁。下面是对Lock接口的几个方法的总体描述：

| 方法名称                         | 描述                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| lock                             | **获取锁**。调用该方法的当前线程将会获得锁。                 |
| lockInterruptibly                | **可中断地获取锁**。和lock不同的是，该方法会响应中断。即在所得获取过程中可以中断当前线程。 |
| tryLock                          | **尝试非阻塞的获取锁**。如果调用的时候能够获取锁，那么就获取锁并且返回true，如果当前的锁无法获取到，那么这个方法会立刻返回false |
| tryLock(long time,TimeUnit unit) | **超时的获取锁**。在指定时间内尝试获取锁如果可以获取锁，那么获取锁并且返回true，如果当前的锁无法获取，那么当前的线程变得不可被调度，直到下面三件事之一发生： 1、当前线程获取到了锁 2、当前线程被其他线程中断 3、指定的等待时间到了 |
| unlock                           | **释放锁**。                                                 |
| newCondition                     | **获取等待通知组件**。返回一个与当前的锁关联的条件变量。在使用这个条件变量之前，当前线程必须占用锁。调用Condition的await方法，会在等待之前原子地释放锁，并在等待被唤醒后原子的获取锁。 |

1. **lock()**

　　首先，lock()方法是平常使用得最多的一个方法，就是用来获取锁。如果锁已被其他线程获取，则进行等待。如果采用Lock，必须主动去释放锁，并且在发生异常时，不会自动释放锁。因此，一般来说，使用Lock必须在try…catch…块中进行，并且将释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。

2. **tryLock() & tryLock(long time, TimeUnit unit)**

　　tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true；如果获取失败（即锁已被其他线程获取），则返回false，也就是说，这个方法无论如何都会立即返回（**在拿不到锁时不会一直在那等待**）。

　　tryLock(long time, TimeUnit unit)方法和tryLock()方法是类似的，只不过区别在于这个方法在拿不到锁时会等待一定的时间，在时间期限之内如果还拿不到锁，就返回false，同时可以响应中断。如果一开始拿到锁或者在等待期间内拿到了锁，则返回true。

3. **lockInterruptibly()**　

　　lockInterruptibly()方法比较特殊，当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。例如，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，假若此时线程A获取到了锁，而线程B只有在等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。

​		注意，当一个线程获取了锁之后，是不会被interrupt()方法中断的。因为interrupt()方法只能中断阻塞过程中的线程而不能中断正在运行过程中的线程。因此，当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，那么只有进行等待的情况下，才可以响应中断的。与 synchronized 相比，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。

　　由于lockInterruptibly()的声明中抛出了异常，所以lock.lockInterruptibly()必须放在try块中或者在调用lockInterruptibly()的方法外声明抛出 InterruptedException，但推荐使用后者。

## 2. AQS原理

AQS的核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待被唤醒时锁分配的机制，这个机制是用CLH队列锁来实现的，即将暂时获取不到锁的线程加入到队列中。

### 2.1 AQS是构建同步组件的基础

####  同步等待队列

AbstractQueuedSynchronizer，简称AQS，为构建不同的同步组件(重入锁,读写锁,CountDownLatch等)提供了可扩展的基础框架。它使用了一个int成员变量表示同步状态，通过内置的FIFO队列来完成资源获取线程的排队工作。

AQS的内部结构主要由**同步等待队列构成**。

```java
/**
 * Head of the wait queue, lazily initialized.  Except for
 * initialization, it is modified only via method setHead.  Note:
 * If head exists, its waitStatus is guaranteed not to be
 * CANCELLED.
 */
private transient volatile Node head;//指向队列首元素的头指针

/**
 * Tail of the wait queue, lazily initialized.  Modified only via
 * method enq to add new wait node.
 */
private transient volatile Node tail;//指向队列尾元素的尾指针
```

head是头指针，指向队列的首元素；tail是尾指针，指向队列的尾元素。

而队列的元素结点**Node定义**在AQS内部，主要有如下几个成员变量。

```java
volatile Node prev; //指向前一个结点的指针
volatile Node next; //指向后一个结点的指针
volatile Thread thread; //当前结点代表的线。因为同步队列中的结点内部封装了之前竞争锁失败的线程,故而结点内部必然有一个对应线程实例的引用
volatile int waitStatus; //等待状态。对于重入锁而言,主要有3个值。0:初始化状态；-1(SIGNAL):当前结点表示的线程在释放锁后需要唤醒后续节点的线程;1(CANCELLED):在同步队列中等待的线程等待超时或者被中断,取消继续等待。
```

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805183911414-329333235.png)

1. 同步队列是个先进先出(FIFO)队列，获取锁失败的线程将构造结点并加入队列的尾部，并阻塞自己。如何才能线程安全的实现入队是后面讲解的重点，毕竟我们在讲锁的实现吗，这部分代码肯定是不能用锁的。

2. 队列首结点可以用来表示当前正获取锁的线程。
3. 当前线程释放锁后将尝试唤醒后续处结点中处于阻塞状态的线程。

#### 同步状态变量

```java
/**
 * The synchronization state.
 */
private volatile int state;
```

这是一个带volatile前缀的int值，是一个类似计数器的东西。在不同的同步组件中有不同的含义。以ReentrantLock为例，**state可以用来表示该锁被线程重入的次数**。当state为0表示该锁不被任何线程持有；当state为1表示线程恰好持有该锁1次(未重入)；当state大于1则表示锁被线程重入state次。因为这是一个会被并发访问的量，为了防止出现可见性问题要用volatile进行修饰。

#### 持有同步状态的线程标志

```java
/**
 * The current owner of exclusive mode synchronization.
 */
private transient Thread exclusiveOwnerThread;
```

这是在独占同步模式下标记持有同步状态的线程。ReentrantLock就是典型的独占同步模式，该变量用来标识锁被哪个线程持有。

**标准代码：**

```java
public class NoFairLockTest {
    public static void main(String[] args) {
        //创建非公平锁
        ReentrantLock lock = new ReentrantLock(false);
        try {
            //加锁
            lock.lock();
            //模拟业务处理用时
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
```



## 3. ReentrantLock的公平锁

在公平锁FairSync中，线程去竞争一个锁，可能成功也可能失败。成功就直接持有资源，不需要进入队列；失败的话进入队列阻塞，等待唤醒后再尝试竞争锁。

**(1) 首先公平锁对应的逻辑是 ReentrantLock 内部静态类 FairSync。**

**(2) ReentrantLock会先从lock()方法中去获取锁。**

```java
final void lock() {
    // 调用 AQS acquire 获取锁
    acquire(1);
}
```

**(3) 调用AQS的acquire()方法。**

方法是AQS中的方法，因为ReentrantLock继承的是AbstractQueuedSynchronizer.class这个类，而且FairSync这个又是ReentrantLock的内部类，所以就直接可以调用acquire()这个方法，（abstract static class Sync extends AbstractQueuedSynchronizer ， ReentrantLock.FairSync.java重入锁中的内部类公平锁又是继承Sync 这个类（Sync .java是ReentrantLock.java中的内部类））

```java
	/**
     * 这个方法也就是lock（）方法的关键方法。tryAcquire获得资源，返回true，直接结束。若未获取资源，新建一个节点插入队尾，
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     */
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&//获取资源立刻结束
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))//没有被中断过，也结束
            selfInterrupt();
    }
```

**(4) 然后是调用FairSync（公平锁中的）tryAcquire(arg)方法，去尝试再次去获取锁**

首先尝试着去获取锁，如果state的当前状态为0，且没有前继线程在等待，表明没有线程占用，此时可以获得锁，然后设置当前线程为独占线程，并返回true，此时不会调用acquireQueued()方法。相反，如果获取锁失败，则会执行`acquireQueued(addWaiter(Node.EXCLUSIVE), arg)`。首先会将当前线程包装成一个Node，插入到双向队列尾。

```java
protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
            //hasQueuedPredecessors()这个方法就是公平锁和非公平锁的不同之处之一
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {//没有前驱节点并且CAS设置成功
                    setExclusiveOwnerThread(current);//设置当前线程为独占线程
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {//这里和非公平锁类似
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }
```

**hasQueuedPredecessors()：**

这个方法目的就是是判断是否有其他线程比当前线程在同步队列中等待的时间更长。有的话，返回 true，否则返回 false。

具体解释就是：进入队列中会有一个队列可能会有多个正在等待的获取锁的线程节点，可能有Head（头结点）、Node1、Node2、Node3、Tail（尾节点），如果此时Node2节点想要去获取锁，在公平锁中他就会先去判断整个队列中是不是还有比我等待时间更长的节点，如果有，就让他先获取锁，如果没有，那我就获取锁（这里就体会到了公平性）。
注意：因为这个Head（头结点）、Node1、Node2、Node3、Tail（尾节点）队列是现今先出队列FIFO的队列，也就是说谁先进来的谁就在前面，也即是谁先进来的等待时间肯定都会比后进来的等待时间较长

## 4. ReentrantLock的非公平锁

**(1) 首先非公平锁对应的逻辑是 ReentrantLock 内部静态类 NoFairSync**

```java
final void lock() {
    /*
     * 这里调用直接 CAS 设置 state 变量，如果设置成功，表明加锁成功。这里并没有像公平锁
     * 那样调用 acquire 方法让线程进入同步队列进行排队，而是直接调用 CAS 抢占锁。抢占失败
     * 再调用 acquire 方法将线程置于队列尾部排队。
     */
    
    //以cas方式尝试将AQS中的state从0更新为1
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());//获取锁成功则将当前线程标记为持有锁的线程,然后直接返回
    else
        acquire(1);//获取锁失败则执行该方法
}
```

首先尝试快速获取锁，以cas的方式将state的值更新为1，只有当state的原值为0时更新才能成功，因为state在ReentrantLock的语境下等同于锁被线程重入的次数，这意味着只有当前锁未被任何线程持有时该动作才会返回成功。若获取锁成功，则将当前线程标记为持有锁的线程，然后整个加锁流程就结束了。若获取锁失败，则执行acquire方法

**(2) 也是去获取锁调用acquire()方法**

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) && //当前线程尝试获取锁,若获取成功返回true,否则false
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) //只有当前线程获取锁失败才会执行者这部分代码
        selfInterrupt();
}
```

**(3) 然后再去调用tryAcquire(arg)方法**

```java
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
```

**(4) 然后进入到nonfairTryAcquire()方法这里并没有调用hasQueuedPredecessors()这个方法**

```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();//获取当前线程实例
    // 获取同步状态
    int c = getState();

    // 如果同步状态 c = 0，表明锁当前没有线程获得，此时可加锁。
    if (c == 0) {
        // 调用 CAS 获取锁，如果失败，则说明有其他线程在竞争获取锁
        if (compareAndSetState(0, acquires)) { 
            // 设置当前线程为锁的持有线程
            setExclusiveOwnerThread(current);
            return true; //获取锁成功,非重入
        }
    }
    //当前线程就是持有锁的线程,说明该锁被重入了
    else if (current == getExclusiveOwnerThread()) {
        // 计算重入后的同步状态值，acquires 一般为1
        int nextc = c + acquires; //计算state变量要更新的值
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        // 设置新的同步状态值
        setState(nextc);
        return true; //获取锁成功,重入
    }
    return false; //走到这里说明尝试获取锁失败
}
```

这是非公平模式下获取锁的通用方法。它囊括了当前线程在尝试获取锁时的所有可能情况：

- **1.当前锁未被任何线程持有(state=0),则以cas方式获取锁,若获取成功则设置exclusiveOwnerThread为当前线程,然后返回成功的结果；若cas失败,说明在得到state=0和cas获取锁之间有其他线程已经获取了锁,返回失败结果。**

- **2.若锁已经被当前线程获取(state>0，exclusiveOwnerThread为当前线程),则将锁的重入次数加1(state+1),然后返回成功结果。因为该线程之前已经获得了锁,所以这个累加操作不用同步。**

- **3.若当前锁已经被其他线程持有(state>0，exclusiveOwnerThread不为当前线程),则直接返回失败结果**

  

  因为我们用state来统计锁被线程重入的次数，所以当前线程尝试获取锁的操作是否成功可以简化为:state值是否成功累加1，是则尝试获取锁成功，否则尝试获取锁失败。

  其实这里还可以思考一个问题：nonfairTryAcquire已经实现了一个囊括所有可能情况的尝试获取锁的方式，为何在刚进入lock方法时还要通过compareAndSetState(0, 1)去获取锁，毕竟后者只有在锁未被任何线程持有时才能执行成功,我们完全可以把compareAndSetState(0, 1)去掉，对最后的结果不会有任何影响。这种在进行通用逻辑处理之前针对某些特殊情况提前进行处理的方式在后面还会看到，一个直观的想法就是它能提升性能，而代价是牺牲一定的代码简洁性。

## 5. 获取锁失败的线程如何安全的加入同步队列:addWaiter()

```java
/**
 * Creates and enqueues node for current thread and given mode.
 *
 * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
 * @return the new node
 */
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);//首先创建一个新节点,并将当前线程实例封装在内部,mode这里为null
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    enq(node);//入队的逻辑这里都有
    return node;
}

```

首先创建了一个新节点，并将当前线程实例封装在其内部，之后我们直接看enq(node)方法就可以了，中间这部分逻辑在enq(node)中都有，之所以加上这部分“重复代码”和尝试获取锁时的“重复代码”一样，对某些特殊情况进行提前处理，牺牲一定的代码可读性换取性能提升。

```java
/**
 * Inserts node into queue, initializing if necessary. See picture above.
 * @param node the node to insert
 * @return node's predecessor
 */
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;//t指向当前队列的最后一个节点,队列为空则为null
        if (t == null) { // Must initialize  //队列为空
            if (compareAndSetHead(new Node())) //构造新结点,CAS方式设置为队列首元素,当head==null时更新成功
                tail = head;//尾指针指向首结点
        } else {  //队列不为空
            node.prev = t;
            if (compareAndSetTail(t, node)) { //CAS将尾指针指向当前结点,当t(原来的尾指针)==tail(当前真实的尾指针)时执行成功
                t.next = node;    //原尾结点的next指针指向当前结点
                return t;
            }
        }
    }
}
```

这里有两个CAS操作:

- compareAndSetHead(new Node()),CAS方式更新head指针,仅当原值为null时更新成功

```java
/**
 * CAS head field. Used only by enq.
 */
private final boolean compareAndSetHead(Node update) {
    return unsafe.compareAndSwapObject(this, headOffset, null, update);
}
```

- compareAndSetTail(t, node),CAS方式更新tial指针,仅当原值为t时更新成功

```java
/**
 * CAS tail field. Used only by enq.
 */
private final boolean compareAndSetTail(Node expect, Node update) {
    return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
}
```

外层的for循环保证了所有获取锁失败的线程经过失败重试后最后都能加入同步队列。因为AQS的同步队列是不带哨兵结点的，故当队列为空时要进行特殊处理，这部分在if分句中。注意当前线程所在的结点不能直接插入空队列，因为阻塞的线程是由前驱结点进行唤醒的。故先要插入一个结点作为队列首元素，当锁释放时由它来唤醒后面被阻塞的线程，从逻辑上这个队列首元素也可以表示当前正获取锁的线程，虽然并不一定真实持有其线程实例。

首先通过new Node()创建一个空结点，然后以CAS方式让头指针指向该结点(该结点并非当前线程所在的结点)，若该操作成功，则将尾指针也指向该结点。这部分的操作流程可以用下图表示：

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805183957849-785041089.png)

当队列不为空,则执行通用的入队逻辑，这部分在else分句中。

首先当前线程所在的结点的前向指针pre指向当前线程认为的尾结点，源码中用t表示。然后以CAS的方式将尾指针指向当前结点，该操作仅当tail=t，即尾指针在进行CAS前未改变时成功。若CAS执行成功，则将原尾结点的后向指针next指向新的尾结点。整个过程如下图所示。

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805184016249-998899089.png)

整个入队的过程并不复杂，是典型的CAS加失败重试的乐观锁策略。其中**只有更新头指针和更新尾指针这两步进行了CAS同步，可以预见高并发场景下性能是非常好的。**

## 6. 线程加入同步队列后会做什么:acquireQueued()

```java
/**
 * Acquires in exclusive uninterruptible mode for thread already in
 * queue. Used by condition wait methods as well as acquire.
 *
 * @param node the node
 * @param arg the acquire argument
 * @return {@code true} if interrupted while waiting
 */
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        //死循环,正常情况下线程只有获得锁才能跳出循环
        for (;;) {
            final Node p = node.predecessor();//获得当前线程所在结点的前驱结点
            //第一个if分句
            if (p == head && tryAcquire(arg)) { 
                setHead(node); //将当前结点设置为队列头结点
                p.next = null; // help GC
                failed = false;
                return interrupted;//正常情况下死循环唯一的出口
            }
            //第二个if分句
            if (shouldParkAfterFailedAcquire(p, node) &&  //判断是否要阻塞当前线程
                parkAndCheckInterrupt())      //阻塞当前线程
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
/**
* Sets head of queue to be node, thus dequeuing. Called only by
* acquire methods.  Also nulls out unused fields for sake of GC
* and to suppress unnecessary signals and traversals.
*
* @param node the node
*/
private void setHead(Node node) {
    head = node;
    node.thread = null;
    node.prev = null;
}
```

这段代码主要的内容都在for循环中，这是一个死循环，主要有两个if分句构成。**第一个if分句中，当前线程首先会判断前驱结点是否是头结点，如果是则尝试获取锁，获取锁成功则会设置当前结点为头结点(更新头指针)**。为什么必须前驱结点为头结点才尝试去获取锁？**因为头结点表示当前正占有锁的线程**，正常情况下该线程释放锁后会通知后面结点中阻塞的线程，阻塞线程被唤醒后去获取锁，这是我们希望看到的。然而还有一种情况，就是前驱结点取消了等待，此时当前线程也会被唤醒，这时候就不应该去获取锁，而是往前回溯一直找到一个没有取消等待的结点，然后将自身连接在它后面。一旦我们成功获取了锁并成功将自身设置为头结点，就会跳出for循环。否则就会执行第二个if分句:确保前驱结点的状态为SIGNAL，然后阻塞当前线程。

```java
/**
 * Checks and updates status for a node that failed to acquire.
 * Returns true if thread should block. This is the main signal
 * control in all acquire loops.  Requires that pred == node.prev.
 *
 * @param pred node's predecessor holding status
 * @param node the node
 * @return {@code true} if thread should block
 */
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL) //状态为SIGNAL
        /*
         * This node has already set status asking a release
         * to signal it, so it can safely park.
         */
        return true;
    if (ws > 0) { //状态为CANCELLED,
        /*
         * Predecessor was cancelled. Skip over predecessors and
         * indicate retry.
         */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else { //状态为初始化状态(ReentrentLock语境下)
        /*
         * waitStatus must be 0 or PROPAGATE.  Indicate that we
         * need a signal, but don't park yet.  Caller will need to
         * retry to make sure it cannot acquire before parking.
         */
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}

```

可以看到针对前驱结点pred的状态会进行不同的处理

1. pred状态为SIGNAL，则返回true，表示要阻塞当前线程。

2. pred状态为CANCELLED，则一直往队列头部回溯直到找到一个状态不为CANCELLED的结点，将当前节点node挂在这个结点的后面。

3. pred的状态为初始化状态，此时通过compareAndSetWaitStatus(pred, ws, Node.SIGNAL)方法将pred的状态改为SIGNAL。

其实这个方法的含义很简单，就是确保当前结点的前驱结点的状态为SIGNAL，SIGNAL意味着线程释放锁后会唤醒后面阻塞的线程。毕竟，只有确保能够被唤醒，当前线程才能放心的阻塞。

**阻塞线程的方法parkAndCheckInterrupt()**

shouldParkAfterFailedAcquire返回true表示应该阻塞当前线程，则会执行parkAndCheckInterrupt方法，这个方法比较简单，底层调用了LockSupport来阻塞当前线程。源码如下:

```java
/**
 * Convenience method to park and then check if interrupted
 *
 * @return {@code true} if interrupted
 */
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}
```

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805184242438-1630878927.png)

**总结：线程在同步队列中会尝试获取锁，失败则被阻塞，被唤醒后会不停的重复这个过程，直到线程真正持有了锁，并将自身结点置于队列头部。**

## 7. 加锁流程总结

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805184331148-1572746602.png)

## 8. 解锁流程

解锁的源码相对简单,源码如下：

```java
public void unlock() {
    sync.release(1);  
}
public final boolean release(int arg) {
    if (tryRelease(arg)) { //释放锁(state-1),若释放后锁可被其他线程获取(state=0),返回true
        Node h = head;
        //当前队列不为空且头结点状态不为初始化状态(0)   
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);  //唤醒同步队列中被阻塞的线程
        return true;
    }
    return false;
}
```

正确找到sync的实现类,找到真正的入口方法,主要内容都在一个if语句中,先看下判断条件tryRelease方法

```java
protected final boolean tryRelease(int releases) {
    int c = getState() - releases; //计算待更新的state值
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) { //待更新的state值为0,说明持有锁的线程未重入,一旦释放锁其他线程将能获取
        free = true; 
        setExclusiveOwnerThread(null);//清除锁的持有线程标记
    }
    setState(c);//更新state值
    return free;
}
```

tryRelease其实只是将线程持有锁的次数减1，即将state值减1，若减少后线程将完全释放锁(state值为0)，则该方法将返回true，否则返回false。由于执行该方法的线程必然持有锁，故该方法不需要任何同步操作。

若当前线程已经完全释放锁，即锁可被其他线程使用,则还应该唤醒后续等待线程。不过在此之前需要进行两个条件的判断：

- h != null是为了防止队列为空,即没有任何线程处于等待队列中，那么也就不需要进行唤醒的操作。
- h.waitStatus != 0是为了防止队列中虽有线程，但该线程还未阻塞，由前面的分析知，线程在阻塞自己前必须设置前驱结点的状态为SIGNAL，否则它不会阻塞自己。

接下来就是唤醒线程的操作，unparkSuccessor(h)源码如下

```java
private void unparkSuccessor(Node node) {
    /*
     * If status is negative (i.e., possibly needing signal) try
     * to clear in anticipation of signalling.  It is OK if this
     * fails or if status is changed by waiting thread.
     */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual. non-cancelled successor.
     */
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        LockSupport.unpark(s.thread);
}
```

**一般情况下只要唤醒后继结点的线程就行了，但是后继结点可能已经取消等待，所以从队列尾部往前回溯，找到离头结点最近的正常结点，并唤醒其线程。**

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805184348967-1221850147.png)

## 9. 公平锁相比非公平锁的不同

公平锁模式下,对锁的获取有严格的条件限制。在同步队列有线程等待的情况下,所有线程在获取锁前必须先加入同步队列。队列中的线程按加入队列的先后次序获得锁。

对比非公平锁,少了非重入式获取锁的方法,这是第一个不同点

接着看获取锁的通用方法tryAcquire()，该方法在线程未进入队列，加入队列阻塞前和阻塞后被唤醒时都会执行。

在真正CAS获取锁之前加了判断,内容如下

```java
public final boolean hasQueuedPredecessors() {
    // The correctness of this depends on head being initialized
    // before tail and on head.next being accurate if the current
    // thread is first in queue.
    Node t = tail; // Read fields in reverse initialization order
    Node h = head;
    Node s;
    return h != t &&
        ((s = h.next) == null || s.thread != Thread.currentThread());
}
```

从方法名我们就可知道这是判断队列中是否有优先级更高的等待线程,队列中哪个线程优先级最高？由于头结点是当前获取锁的线程，队列中的第二个结点代表的线程优先级最高。
那么我们只要**判断队列中第二个结点是否存在以及这个结点是否代表当前线程就行了**。这里分了两种情况进行探讨:

1. 第二个结点已经完全插入，但是这个结点是否就是当前线程所在结点还未知，所以通过s.thread != Thread.currentThread()进行判断，如果为true，说明第二个结点代表其他线程。
2. 第二个结点并未完全插入,我们知道结点入队一共分三步：

- 1. **待插入结点的pre指针指向原尾结点**
- 2. **CAS更新尾指针**
- 3. **原尾结点的next指针指向新插入结点**

所以(s = h.next) == null 就是用来判断2刚执行成功但还未执行3这种情况的。这种情况第二个结点必然属于其他线程。
以上两种情况都会使该方法返回true，即当前有优先级更高的线程在队列中等待，那么当前线程将不会执行CAS操作去获取锁，保证了线程获取锁的顺序与加入同步队列的顺序一致，很好的保证了公平性，但也增加了获取锁的成本。

## 10. 为什么基于FIFO的同步队列可以实现非公平锁？

由FIFO队列的特性知，先加入同步队列等待的线程会比后加入的线程更靠近队列的头部，那么它将比后者更早的被唤醒，它也就能更早的得到锁。从这个意义上，对于在同步队列中等待的线程而言，它们获得锁的顺序和加入同步队列的顺序一致，这显然是一种公平模式。然而，线程并非只有在加入队列后才有机会获得锁，哪怕同步队列中已有线程在等待，非公平锁的不公平之处就在于此。回看下非公平锁的加锁流程，**线程在进入同步队列等待之前有两次抢占锁的机会:**

- **第一次是非重入式的获取锁，只有在当前锁未被任何线程占有(包括自身)时才能成功;**
- **第二次是在进入同步队列前，包含所有情况的获取锁的方式。**

**只有这两次获取锁都失败后，线程才会构造结点并加入同步队列等待。而线程释放锁时是先释放锁(修改state值)，然后才唤醒后继结点的线程的。**

试想下这种情况，**线程A已经释放锁，但还没来得及唤醒后继线程C，而这时另一个线程B刚好尝试获取锁，此时锁恰好不被任何线程持有，它将成功获取锁而不用加入队列等待。线程C被唤醒尝试获取锁，而此时锁已经被线程B抢占，故而其获取失败并继续在队列中等待。**

![img](https://images2018.cnblogs.com/blog/1422237/201808/1422237-20180805185020232-615609336.png)

**很重要：如果以线程第一次尝试获取锁到最后成功获取锁的次序来看，非公平锁确实很不公平。因为在队列中等待很久的线程相比还未进入队列等待的线程并没有优先权，至竞争也处于劣势：在队列中的线程要等待其他线程唤醒，在获取锁之前还要检查前驱结点是否为头结点。在锁竞争激烈的情况下，在队列中等待的线程可能迟迟竞争不到锁。**这也就非公平在高并发情况下会出现的饥饿问题。那我们再开发中为什么大多使用会导致饥饿的非公平锁？很简单，因为它性能好啊。

### 为什么非公平锁性能好

非公平锁对锁的竞争是抢占式的(队列中线程除外)，线程在进入等待队列前可以进行两次尝试，这大大增加了获取锁的机会。这种好处体现在两个方面:

- 1.**线程不必加入等待队列就可以获得锁，不仅免去了构造结点并加入队列的繁琐操作，同时也节省了线程阻塞唤醒的开销，线程阻塞和唤醒涉及到线程上下文的切换和操作系统的系统调用，是非常耗时的。**在高并发情况下，如果线程持有锁的时间非常短,短到线程入队阻塞的过程超过线程持有并释放锁的时间开销，那么这种抢占式特性对并发性能的提升会更加明显。
- 2.**减少CAS竞争**。如果线程必须要加入阻塞队列才能获取锁，那入队时CAS竞争将变得异常激烈，CAS操作虽然不会导致失败线程挂起，但不断失败重试导致的对CPU的浪费也不能忽视。除此之外，加锁流程中至少有两处通过将某些特殊情况提前来减少CAS操作的竞争，增加并发情况下的性能。**一处就是获取锁时将非重入的情况提前。另一处就是入队的操作,将同步队列非空的情况提前处理。**lock代码和addWaiter代码中。

这两部分的代码在之后的通用逻辑处理中都有，很显然属于重复代码，但因为避免了执行无意义的流程代码，比如for循环，获取同步状态等，高并发场景下也能减少CAS竞争失败的可能。