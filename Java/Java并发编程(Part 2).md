# Java并发编程(Part 2)
## 1 创建和运行线程
有三种使用线程的方法：
* 实现 Runnable 接口；
* 实现 Callable 接口；
* 继承 Thread 类。
  
实现 Runnable 和 Callable 接口的类只能当做一个可以在线程中运行的任务，不是真正意义上的线程，因此最后还需要通过 Thread 来调用。可以理解为任务是通过线程驱动从而执行的。

1. 继承 Thread 类

Thread 类实现了 Runable 接口。

当调用 start() 方法启动一个线程时，虚拟机会将该线程放入就绪队列中等待被调度，当一个线程被调度时会执行该线程的 run() 方法。

```java
// 创建线程对象
Thread t = new Thread() {
    // run 方法内实现了要执行的任务
    public void run() {
    // 要执行的任务
    }
};
// 启动线程
t.start();
```
```java
public class MyThread extends Thread {
    public void run() {
        // ...
    }
}

public static void main(String[] args) {
    MyThread mt = new MyThread();
    mt.start();
}
```

2. 实现 Runnable 接口

把线程和任务（要执行的代码）分开，Thread 代表线程，Runnable 可运行的任务（线程要执行的代码）。
1. 先实现接口中的 run() 方法。
2. 使用 Runnable 实例再创建一个 Thread 实例，然后调用 Thread 实例的 start() 方法来启动线程。
```java
Runnable runnable = new Runnable() {
    public void run(){
        // 要执行的任务
    }
};
// 创建线程对象
Thread t = new Thread( runnable );
// 启动线程
t.start();

```

```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        // ...
    }
}

public static void main(String[] args) {
    MyRunnable instance = new MyRunnable();
    Thread thread = new Thread(instance);
    thread.start();
}
```

```java
//使用lamba进行简化
// 创建任务对象
Runnable task2 = () -> log.debug("hello");
// 参数1 是任务对象; 参数2 是线程名字，推荐
Thread t2 = new Thread(task2, "t2");
t2.start();
```
总结：
用 Runnable 更容易与线程池等高级 API 配合。
用 Runnable 让任务类脱离了 Thread 继承体系，更灵活。

3. 实现 Callable 接口
   
与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装。
```java
// 创建任务对象
FutureTask<Integer> task3 = new FutureTask<>(() -> {
    log.debug("hello");
    return 100;
});
// 参数1 是任务对象; 参数2 是线程名字，推荐
new Thread(task3, "t3").start();
// 主线程阻塞，同步等待 task 执行完毕的结果
Integer result = task3.get();
log.debug("结果是:{}", result);
```

```java
public class MyCallable implements Callable<Integer> {
    public Integer call() {
        return 123;
    }
}
public static void main(String[] args) throws ExecutionException, InterruptedException {
    MyCallable mc = new MyCallable();
    FutureTask<Integer> ft = new FutureTask<>(mc);
    Thread thread = new Thread(ft);
    thread.start();
    System.out.println(ft.get());
}
```

实现接口 VS 继承 Thread
实现接口会更好一些，因为：
1. Java 不支持多重继承，因此继承了 Thread 类就无法继承其它类，但是可以实现多个接口；
2. 类可能只要求可执行就行，继承整个 Thread 类开销过大。

## 2. Java线程基础知识
### 2.1 Executor
Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。

主要有三种 Executor：
* CachedThreadPool：一个任务创建一个线程；
* FixedThreadPool：所有任务只能使用固定大小的线程；
* SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        executorService.execute(new MyRunnable());
    }
    executorService.shutdown();
}
```

Executor 的中断操作：调用 Executor 的 shutdown() 方法会等待线程都执行完毕之后再关闭，但是如果调用的是 shutdownNow() 方法，则相当于调用每个线程的 interrupt() 方法。

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> {
        try {
            Thread.sleep(2000);
            System.out.println("Thread run");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    executorService.shutdownNow();
    System.out.println("Main run");
}
```


### 2.2 Daemon(守护线程)
守护线程是程序运行时在后台提供服务的线程，不属于程序中不可或缺的部分。

当所有**非守护线程结束**时，程序也就终止，同时会杀死所有守护线程。

main()属于非守护线程。在java程序中垃圾回收器线程就是一种守护线程。在线程启动之前使用 setDaemon() 方法可以将一个线程设置为守护线程。

```java
public static void main(String[] args) {
    Thread thread = new Thread(new MyRunnable());
    thread.setDaemon(true);
}
```

### 2.3 start() 和 run()
start() 启动一个新线程，在新的线程运行 run 方法中的代码。start 方法只是让线程进入就绪，里面代码不一定立刻运行（CPU 的时间片还没分给它）。每个线程对象的start方法只能调用一次，如果调用了多次会出现IllegalThreadStateException。

run() 新线程启动后会调用的方法。

直接在 main 中调用 run 是在主线程中执行了 run，没有启动新的线程。使用 start 是启动新的线程，通过新的线程间接执行 run 中的代码。

### 2.4 sleep() 和 yield()
sleep()(Thread.sleep()):

1. 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞）
2. 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException
3. 睡眠结束后的线程未必会立刻得到执行
4. 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性
   
yield()(Thread.yield()): 
1. 调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程。声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行。
2. 线程优先级会提示调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它。如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用。

### 2.5 interrupt()
#### 2.5.1 打断 sleep，wait，join 的线程
这几个方法都会让线程进入阻塞状态。如果该线程处于阻塞、限期等待或者无限期等待状态, 对该线程进行中断，那么就会抛出 InterruptedException，从而提前结束该线程，会清空打断状态。但是不能中断 I/O 阻塞和 synchronized 锁阻塞。

中断标记变成false。t1.isInterrupted() --> false
#### 2.5.2 打断正常运行的线程
如果一个线程的 run() 方法执行一个无限循环，并且没有执行 sleep() 等会抛出 InterruptedException 的操作，那么调用线程的 interrupt() 方法就无法使线程提前结束。

但是调用 interrupt() 方法会设置线程的中断标记，此时调用 interrupted() 方法会返回 true。因此可以在循环体中使用 interrupted() 方法来判断线程是否处于中断状态，从而提前结束线程。

中断标记变成false。t1.isInterrupted() --> true

```java
private static void test2() throws InterruptedException {
    Thread t2 = new Thread(()->{
    while(true) {
            Thread current = Thread.currentThread();
            boolean interrupted = current.isInterrupted();
            if(interrupted) {
                log.debug(" 中断状态: {}", interrupted);
                break;
            }
        }
    }, "t2");
    t2.start();
    sleep(0.5);
    t2.interrupt();
}
```
#### 2.5.3 打断 park 线程
打断 park 线程, 不会清空中断标记。如果中断标记已经是 true, 则 park 会失效。


#### 2.5.4 5种状态(一般意义上的)
1. (初始状态)： 仅是在语言层面创建了线程对象，还未与操作系统线程关联
2. (可运行状态)（就绪状态）：指该线程已经被创建（与操作系统线程关联），可以由 CPU 调度执行
3. (运行状态) ：指获取了 CPU 时间片运行中的状态
* 当 CPU 时间片用完，会从(运行状态)转换至(可运行状态)，会导致线程的上下文切换
4. (阻塞状态)：
* 如果调用了阻塞 API，如 BIO 读写文件，这时该线程实际不会用到 CPU，会导致线程上下文切换，进入(阻塞状态)。等 BIO 操作完毕，会由操作系统唤醒阻塞的线程，转换至(可运行状态)
* 与(可运行状态)的区别是，对(阻塞状态)的线程来说只要它们一直不唤醒，调度器就一直不会考虑调度它们。
5. (终止状态)：表示线程已经执行完毕，生命周期已经结束，不会再转换为其它状态。

#### 2.5.5 6种状态(Java语言意义上的)
1. **NEW** 线程刚被创建，但是还没有调用 start() 方法
2. **RUNNABLE** 当调用了 start() 方法之后，注意，**Java API** 层面的 RUNNABLE 状态涵盖了操作系统层面的**可运行状态**、**运行状态**和**阻塞状态**（由于 BIO 导致的线程阻塞，在 Java 里无法区分，仍然认为
是可运行）
1. **BLOCKED** ， **WAITING** ， **TIMED_WAITING** 都是 Java API 层面对**阻塞状态**的细分。
2. **TERMINATED** 当线程代码运行结束。

## 3. 互斥同步
互斥指的是：保证临界区的代码不被上下文切换而产生指令的交错，保证临界区代码的原子性。使用 synchronized 或 Lock 达到共享资源互斥效果。

同步指的是：保证在条件不满足条件时，让线程等待，当条件满足后，让线程恢复运行。使用 wait/notify 或 Lock 的条件变量来达到线程间通信效果。同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点。

### 3.1 相关概念
临界区(Critical Section): 一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区。在多个线程对共享资源读写操作时发生指令交错，就会出现问题。互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码。

竞态条件(Race Condition): 多个线程在临界区内执行，由于代码的执行序列不同而导致结果无法预测，称之为发生了竞态条件。

### 3.1 阻塞式的解决方案：synchronized，Lock
Java 提供了两种锁机制来控制多个线程对共享资源的互斥访问，第一个是 JVM 实现的 synchronized，而另一个是 JDK 实现的 ReentrantLock。

#### 3.1.1 synchronized 解决方案
synchronized，即俗称的对象锁，它采用互斥的方式让同一时刻至多只有一个线程能持有对象锁，其它线程再想获取这个对象锁时就会阻塞住。这样就能保证拥有锁的线程可以安全的执行临界区内的代码，不用担心线程上下文切换。synchronized 实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可分割的，不会被线程切换所打断。synchronized只能作用于函数或代码块。通过不同的锁粒度可以提高运行的效率。

synchronized语法
1. 同步一个代码块
   
它只作用于同一个对象，如果调用两个对象上的同步代码块，就不会进行同步。
```java
public void func() {
    synchronized (this) {
        // ...
    }
}
```
2. 同步一个方法

和同步代码块一样，作用于同一个对象。与1等价。
```java
public synchronized void func () {
    // ...
}
```

3. 同步一个类
作用于该类的类对象。作用于整个类，也就是说两个线程调用同一个类的不同对象上的这种同步语句，也会进行同步。
```java
public void func() {
    synchronized (SynchronizedExample.class) {
        // ...
    }
}
```

4. 同步一个静态方法
与同步一个类一样，作用于该类的类对象。与3等价。
```java
public synchronized static void fun() {
    // ...
}
```

面向对象实现方式：（把需要保护的共享变量放入一个类）
```java
class Room {
    int value = 0;
    public void increment() {
        synchronized (this) {
            value++;
        }
    }
    public void decrement() {
        synchronized (this) {
            value--;
        }
    }
    public int get() {
        synchronized (this) {
            return value;
        }
    }
}

@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.decrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count: {}" , room.get());
    }
}
```

### 3.2 非阻塞式的解决方案：原子变量(后面的Part进行介绍)

### 3.3 变量的线程安全分析
成员变量和静态变量是否线程安全？
* 如果它们没有共享，则线程安全
* 如果它们被共享了，根据它们的状态是否能够改变，又分两种情况
  * 如果只有读操作，则线程安全
  * 如果有读写操作，则这段代码是临界区，需要考虑线程安全

局部变量是否线程安全？
* 局部变量是线程安全的
* 但局部变量引用的对象则未必
  * 如果该对象没有逃离方法的作用访问，它是线程安全的
  * 如果该对象逃离方法的作用范围，需要考虑线程安全

#### 3.3.1 常见的线程安全类
* String
* Integer
* StringBuffer
* Random
* Vector
* Hashtable
* java.util.concurrent 包下的类

这里说它们是线程安全的是指，多个线程调用它们同一个实例的某个方法时，是线程安全的。比如HashTbale的put方法。它们的每个方法是原子的，但是它们多个方法的组合不是原子的。

String、Integer 等都是不可变类，因为其内部的状态不可以改变，因此它们的方法都是线程安全的。一些加了final修饰的变量也可以认为是线程安全的。

### 3.4 synchronized 进阶知识
synchronized 在JVM的实现原理，JVM基于进入和退出Monitor对象来实现方法同步和代码块同步。代码块的同步使用monitorendter和monitorexit指令来实现，方法块同步使用另一种方式实现，在JVM中并没有说明， 但同样可以使用这两个指令来实现。monitorenter指令实现编译后插入到同步代码块开始位置，monitorexit则是插入到方法结束和异常处。JVM要保证每个monitorenter必须有对应的monitorexit对应。

任何对象都有一个monitor相关联，当一个monitor被持有后，它将处于锁定状态，线程执行到monitorenter指令时，将尝试获得对象所对应的monitor的所有权，即尝试获得对象的锁。

#### 3.4.1 Monitor(管程或监视器)(主要指的是重量级锁)
每个 Java 对象都可以关联一个 Monitor 对象，如果使用 synchronized 给对象上锁（重量级）之后，该对象头的 Mark Word 中就被设置指向 Monitor 对象的指针。

Monitor中主要包含了Owner，EntryList，WaitSet。Owner指向锁的持有者，EntryList因竞争锁而阻塞的线程(由notify和notifyall来唤醒)，WaitSet表示因wait()而进入等待状态的线程。

1. 刚开始 Monitor 中 Owner 为 null
2. 当 Thread-2 执行 synchronized(obj) 就会将 Monitor 的所有者 Owner 置为 Thread-2，Monitor中只能有一个 Owner
3. 在 Thread-2 上锁的过程中，如果 Thread-3，Thread-4，Thread-5 也来执行 synchronized(obj)，就会进入
EntryList BLOCKED
4. Thread-2 执行完同步代码块的内容，然后唤醒 EntryList 中等待的线程来竞争锁，竞争的时是非公平的
WaitSet 中的 Thread-0，Thread-1 是之前获得过锁，但条件不满足进入 WAITING 状态的线程。

#### 3.4.2 Java对象头
synchronized锁存储在java对象头内部，如果对象是非数组类型，虚拟机用2字宽存储对象头，如果对象是数组类型，虚拟机用3字宽存储对象头。1字宽在32位虚拟机等于4字节，32bit。普通对象的对象头包括1字宽的Mark word(默认存储Hashcode，分代年龄，锁标记位)，1字宽的Class Metadata Address(存储对象类型数据的指针)。

#### 3.4.2 轻量级锁
如果一个对象虽然有多线程要加锁，但**加锁的时间是错开**的（也就是没有竞争），那么可以使用轻量级锁来优化。

1. 创建锁记录（Lock Record）对象。线程在执行同步块之前，JVM会在当前线程的栈帧中创建用于存储锁记录(Lock Record)的空间，内部可以存储锁定对象的Mark Word。
2. 让锁记录中 Object reference 指向锁对象，并尝试用 CAS 替换 Object 的 Mark Word，将 Mark Word 的值存入锁记录。
3. 如果 CAS 替换成功，对象头中存储了锁记录地址和状态 00 ，表示由该线程给对象加锁。
4. 如果 CAS 失败，有两种情况
   * 如果是其它线程已经持有了该 Object 的轻量级锁，这时表明有竞争，会自旋一段时间等待锁的释放 (自旋消耗CPU) ，如果依旧失败，进入锁膨胀过程
   * 如果是自己执行了 synchronized 锁重入，那么再添加一条 Lock Record 作为重入的计数
5. 当退出 synchronized 代码块（解锁时）如果有取值为 null 的锁记录，表示有重入，这时重置锁记录，表示重
入计数减一
6. 当退出 synchronized 代码块（解锁时）锁记录的值不为 null，这时使用 CAS 将 Mark Word 的值恢复给对象
头
   * 成功，则解锁成功
   * 失败，说明轻量级锁进行了锁膨胀或已经升级为重量级锁，进入重量级锁解锁流程

#### 3.4.3 锁膨胀
如果在尝试加轻量级锁的过程中，CAS 操作无法成功，这时一种情况就是有其它线程为此对象加上了轻量级锁（有
竞争），这时需要进行锁膨胀，将轻量级锁变为重量级锁。

1. 当 Thread-1 进行轻量级加锁时，Thread-0 已经对该对象加了轻量级锁。
2. 这时 Thread-1 加轻量级锁失败，进入锁膨胀流程
   * 即为 Object 对象申请 Monitor 锁，让 Object 指向重量级锁地址
   * 然后自己进入 Monitor 的 EntryList BLOCKED
3. 当 Thread-0 退出同步块解锁时，使用 cas 将 Mark Word 的值恢复给对象头，失败。这时会进入重量级解锁
流程，即按照 Monitor 地址找到 Monitor 对象，设置 Owner 为 null，唤醒 EntryList 中 BLOCKED 线程

#### 3.4.4 自旋优化
轻量级锁竞争的时候，还可以使用自旋来进行优化，如果当前线程自旋成功（即这时候持锁线程已经退出了同步
块，释放了锁），这时当前线程就可以避免阻塞。而重量级锁不使用自旋，不会消耗CPU。

#### 3.4.5 偏向锁
轻量级锁在没有竞争时（就自己这个线程，适用于一个线程访问同步块的场景），每次重入仍然需要执行 CAS 操作。

只有第一次使用 CAS 将线程 ID 设置到对象的 Mark Word 头，之后发现这个线程 ID 是自己的就表示没有竞争，不用重新 CAS。以后只要不发生竞争，这个对象就归该线程所有

一个对象创建时：
1. 如果开启了偏向锁（默认开启），那么对象创建后，markword 值为 0x05 即最后 3 位为 101，这时它的
thread、epoch、age 都为 0
2. 偏向锁是默认是延迟的，不会在程序启动时立即生效，如果想避免延迟，可以加 VM 参数 -
XX:BiasedLockingStartupDelay=0 来禁用延迟
3. 如果没有开启偏向锁，那么对象创建后，markword 值为 0x01 即最后 3 位为 001，这时它的 hashcode、
age 都为 0，第一次用到 hashcode 时才会赋值。

其它线程使用对象

当有其它线程使用偏向锁对象时，会将偏向锁升级为轻量级锁。调用 wait/notify 后锁会变成重量级锁。

批量重偏向： 如果对象虽然被多个线程访问，但没有竞争，这时偏向了线程 T1 的对象仍有机会重新偏向 T2，重偏向会重置对象的 Thread ID。当撤销偏向锁阈值超过 20 次后，jvm 会这样觉得，我是不是偏向错了呢，于是会在给这些对象加锁时重新偏向至加锁线程。

批量撤销： 当撤销偏向锁阈值超过 40 次后，jvm 会这样觉得，自己确实偏向错了，根本就不该偏向。于是整个类的所有对象都会变为不可偏向的，新建的对象也是不可偏向的。

#### 3.4.6 wait() notify() notifyAll()
* obj.wait() 让进入 object 监视器的线程到 waitSet 等待
* obj.notify() 在 object 上正在 waitSet 等待的线程中挑一个唤醒
* obj.notifyAll() 让 object 上正在 waitSet 等待的线程全部唤醒

它们都是线程之间进行协作的手段，都属于 Object 对象的方法。必须获得此对象的锁，才能调用这几个方法

sleep(long n) 和 wait(long n) 的区别
1) sleep 是 Thread 方法，而 wait 是 Object 的方法 
2) sleep 不需要强制和 synchronized 配合使用，但 wait 需要和 synchronized 一起用 
3) sleep 在睡眠的同时，不会释放对象锁的，但 wait 在等待的时候会释放对象锁 
4) 它们状态都是 TIMED_WAITING

#### 3.4.7 wait notify 原理
1. Owner 线程发现条件不满足，调用 wait 方法，即可进入 WaitSet 变为 WAITING 状态
2. BLOCKED 和 WAITING 的线程都处于阻塞状态，不占用 CPU 时间片
3. BLOCKED 线程会在 Owner 线程释放锁时唤醒
4. WAITING 线程会在 Owner 线程调用 notify 或 notifyAll 时唤醒，但唤醒后并不意味者立刻获得锁，仍需进入
EntryList 重新竞争

#### 3.4.8 join 原理
```java
t1.join();
等价于：
synchronized (t1) {
    // 调用者线程进入 t1 的 waitSet 等待, 直到 t1 运行结束
    while (t1.isAlive()) {
        t1.wait(0);
    }
}
```

#### 3.4.9 park() unpark()
它们是 LockSupport 类中的方法
```
// 暂停当前线程
LockSupport.park();
// 恢复某个线程的运行
LockSupport.unpark(暂停线程对象)
```
与 Object 的 wait & notify 相比
1. wait，notify 和 notifyAll 必须配合 Object Monitor 一起使用，而 park，unpark 不必
2. park & unpark 是以线程为单位来阻塞和唤醒线程，而 notify 只能随机唤醒一个等待线程，notifyAll是唤醒所有等待线程，就不那么精确。
3. park & unpark 可以先 unpark，而 wait & notify 不能先 notify


#### 3.4.10 park unpark 原理
每个线程都有自己的一个 Parker 对象，由三部分组成 _counter ， _cond 和 _mutex。可以提前进行unpark。

---
1. 当前线程调用 Unsafe.park() 方法
2. 检查 _counter ，本情况为 0，这时，获得 _mutex 互斥锁
3. 线程进入 _cond 条件变量阻塞
4. 设置 _counter = 0
---
1. 调用 Unsafe.unpark(Thread_0) 方法，设置 _counter 为 1
2. 唤醒 _cond 条件变量中的 Thread_0
3. Thread_0 恢复运行
4. 设置 _counter 为 0
---
1. 调用 Unsafe.unpark(Thread_0) 方法，设置 _counter 为 1
2. 当前线程调用 Unsafe.park() 方法
3. 检查 _counter ，本情况为 1，这时线程无需阻塞，继续运行
4. 设置 _counter 为 0
---

#### 3.4.11 线程状态转换
情况 1 NEW --> RUNNABLE
* 当调用 t.start() 方法时，由 NEW --> RUNNABLE

情况 2 RUNNABLE <--> WAITING
t 线程用 synchronized(obj) 获取了对象锁后
* 调用 obj.wait() 方法时，t 线程从 RUNNABLE --> WAITING
* 调用 obj.notify() ， obj.notifyAll() ， t.interrupt() 时
   * 竞争锁成功，t 线程从 WAITING --> RUNNABLE
   * 竞争锁失败，t 线程从 WAITING --> BLOCKED

情况 3 RUNNABLE <--> WAITING
* 当前线程调用 t.join() 方法时，当前线程从 RUNNABLE --> WAITING
   * 注意是当前线程在t 线程对象的监视器上等待
* t 线程运行结束，或调用了当前线程的 interrupt() 时，当前线程从 WAITING --> RUNNABLE

情况 4 RUNNABLE <--> WAITING
* 当前线程调用 LockSupport.park() 方法会让当前线程从 RUNNABLE --> WAITING
* 调用 LockSupport.unpark(目标线程) 或调用了线程 的 interrupt() ，会让目标线程从 WAITING -->
RUNNABLE

情况 5 RUNNABLE <--> TIMED_WAITING
t 线程用 synchronized(obj) 获取了对象锁后
* 调用 obj.wait(long n) 方法时，t 线程从 RUNNABLE --> TIMED_WAITING
* t 线程等待时间超过了 n 毫秒，或调用 obj.notify() ， obj.notifyAll() ， t.interrupt() 时
   * 竞争锁成功，t 线程从 TIMED_WAITING --> RUNNABLE
   * 竞争锁失败，t 线程从 TIMED_WAITING --> BLOCKED

情况 6 RUNNABLE <--> TIMED_WAITING
* 当前线程调用 t.join(long n) 方法时，当前线程从 RUNNABLE --> TIMED_WAITING
   * 注意是当前线程在t 线程对象的监视器上等待
* 当前线程等待时间超过了 n 毫秒，或t 线程运行结束，或调用了当前线程的 interrupt() 时，当前线程从TIMED_WAITING --> RUNNABLE

情况 7 RUNNABLE <--> TIMED_WAITING
* 当前线程调用 Thread.sleep(long n) ，当前线程从 RUNNABLE --> TIMED_WAITING
* 当前线程等待时间超过了 n 毫秒，当前线程从 TIMED_WAITING --> RUNNABLE

情况 8 RUNNABLE <--> TIMED_WAITING
* 当前线程调用 LockSupport.parkNanos(long nanos) 或 LockSupport.parkUntil(long millis) 时，当前线
程从 RUNNABLE --> TIMED_WAITING
* 调用 LockSupport.unpark(目标线程) 或调用了线程 的 interrupt() ，或是等待超时，会让目标线程从
TIMED_WAITING--> RUNNABLE

情况 9 RUNNABLE <--> BLOCKED
* t 线程用 synchronized(obj) 获取了对象锁时如果竞争失败，从 RUNNABLE --> BLOCKED
* 持 obj 锁线程的同步代码块执行完毕，会唤醒该对象上所有 BLOCKED 的线程重新竞争，如果其中 t 线程竞争
成功，从 BLOCKED --> RUNNABLE ，其它失败的线程仍然 BLOCKED

情况 10 RUNNABLE <--> TERMINATED
* 当前线程所有代码运行完毕，进入TERMINATED

### 3.5 ReentrantLock
ReentrantLock 是 java.util.concurrent（J.U.C）包中的锁。

相对于 synchronized 它具备如下特点
1. 等待可中断
2. 可以设置超时时间
3. 可以设置为公平锁
4. 支持多个条件变量
5. 锁的实现: synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
   
### 3.5.1 实现方法
基本语法
```java
// 获取锁
reentrantLock.lock();
try {
    // 临界区
    } finally {
    // 释放锁
        reentrantLock.unlock();
}
```
```java
public class LockExample {

    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        } finally {
            lock.unlock(); // 确保释放锁，从而避免发生死锁。
        }
    }
}

public static void main(String[] args) {
    LockExample lockExample = new LockExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> lockExample.func());
    executorService.execute(() -> lockExample.func());
}
```

#### 3.5.2 可重入
可重入是指同一个线程如果首次获得了这把锁，那么因为它是这把锁的拥有者，因此有权利再次获取这把锁
如果是不可重入锁，那么第二次获得锁时，自己也会被锁挡住。

#### 3.5.3 等待可中断
如果没有竞争此方法会获取到锁，如果有竞争就进入阻塞队列，可以被其他线程用interrupt方法打断。

```java
lock.lockInterruptibly(); //可中断
t1.interrupt();

lock.lock(); //不可中断模式，那么即使使用了 interrupt 也不会让等待中断
t1.interrupt(); //这时 t1 并没有被真正打断, 而是仍继续等待锁
```

#### 3.5.4 锁超时
```java
如果能获得锁就获得锁，并返回true，否则返回false。
lock.tryLock()
lock.tryLock(1, TimeUnit.SECONDS)
```

#### 3.5.5 公平锁
ReentrantLock 默认是不公平的
```java
ReentrantLock lock = new ReentrantLock(false);
ReentrantLock lock = new ReentrantLock(true); //公平锁
```

#### 3.5.6 条件变量
ReentrantLock 的条件变量比 synchronized 强大之处在于，它是支持多个条件变量的。
使用要点：
* await 前需要获得锁
* await 执行后，会释放锁，进入 conditionObject 等待
* await 的线程被唤醒（或打断、或超时）取重新竞争 lock 锁
* 竞争 lock 锁成功后，从 await 后继续执行

```java
Condition a = lock.newCondition();
Condition b = lock.newCondition();
```

### 3.6 活跃性
#### 3.6.1 死锁
一个线程需要同时获取多把锁，这时就容易发生死锁。t1 线程 获得 A对象 锁，接下来想获取 B对象的锁 t2 线程 获得 B对象 锁，接下来想获取 A对象的锁。



#### 3.6.2 活锁
活锁出现在两个线程互相改变对方的结束条件，最后谁也无法结束。

#### 3.6.3 饥饿
一个线程由于优先级太低，始终得不到 CPU 调度执行，也不能够结束。（顺序加锁）

### 3.7 异步模式之生产者/消费者
#### 3.7.1 定义
1. 消费队列可以用来平衡生产和消费的线程资源
2. 生产者仅负责产生结果数据，不关心数据该如何处理，而消费者专心处理结果数据
3. 消息队列是有容量限制的，满时不会再加入数据，空时不会再消耗数据

#### 3.7.2 实现
```java
public class ProducerConsumer {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        // 4 个生产者线程, 下载任务
        for (int i = 0; i < 4; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    log.debug("download...");
                    List<String> response = Downloader.download();
                    log.debug("try put message({})", id);
                    messageQueue.put(new Message(id, response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "生产者" + i).start();
        }
// 1 个消费者线程, 处理结果
        new Thread(() -> {
            while (true) {
                Message message = messageQueue.take();
                List<String> response = (List<String>) message.getMessage();
                log.debug("take message({}): [{}] lines", message.getId(), response.size());
            }
        }, "消费者").start();
    }
    class Message {
        private int id;
        private Object message;
        public Message(int id, Object message) {
            this.id = id;
            this.message = message;
        }
        public int getId() {
            return id;
        }
        public Object getMessage() {
            return message;
        }
    }
    class MessageQueue {
        private LinkedList<Message> queue;
        private int capacity;
        public MessageQueue(int capacity) {
            this.capacity = capacity;
            queue = new LinkedList<>();
        }
        public Message take() {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    log.debug("没货了, wait");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = queue.removeFirst();
                queue.notifyAll();
                return message;
            }
        }
        public void put(Message message) {
            synchronized (queue) {
                while (queue.size() == capacity) {
                    log.debug("库存已达上限, wait");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.addLast(message);
                queue.notifyAll();
            }
        }
    }
}
```

#### 3.8 交替输出
线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。现在要求输出 abcabcabcabcabc 怎么实现

#### 3.8.1 wait notify 版
```java
class SyncWaitNotify {
    private int flag;
    private int loopNumber;
    public SyncWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    public void print(int waitFlag, int nextFlag, String str) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
```

```java
public static void main(String[] args) {
    SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 5);
    new Thread(() -> {
        syncWaitNotify.print(1, 2, "a");
    }).start();
    new Thread(() -> {
        syncWaitNotify.print(2, 3, "b");
    }).start();
    new Thread(() -> {
        syncWaitNotify.print(3, 1, "c");
    }).start();
}
```

#### 3.8.2 Lock 条件变量版
```java
class AwaitSignal extends ReentrantLock {
    public void start(Condition first) {
        this.lock();
        try {
            log.debug("start");
            first.signal();
        } finally {
            this.unlock();
        }
    }
    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            this.lock();
            try {
                current.await();
                log.debug(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.unlock();
            }
        }
    }
    // 循环次数
    private int loopNumber;
    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
```

```java
public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition aWaitSet = as.newCondition();
        Condition bWaitSet = as.newCondition();
        Condition cWaitSet = as.newCondition();
        new Thread(() -> {
            as.print("a", aWaitSet, bWaitSet);
        }).start();
        new Thread(() -> {
            as.print("b", bWaitSet, cWaitSet);
        }).start();
        new Thread(() -> {
            as.print("c", cWaitSet, aWaitSet);
        }).start();
        as.start(aWaitSet);
    }
```

#### 3.8.3 Park Unpark 版
```java
class SyncPark {
    private int loopNumber;
    private Thread[] threads;
    public SyncPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
    public void setThreads(Thread... threads) {
        this.threads = threads;
    }
    public void print(String str) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(nextThread());
        }
    }
    private Thread nextThread() {
        Thread current = Thread.currentThread();
        int index = 0;
        for (int i = 0; i < threads.length; i++) {
            if(threads[i] == current) {
                index = i;
                break;
            }
        }
        if(index < threads.length - 1) {
            return threads[index+1];
        } else {
            return threads[0];
        }
    }
    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
        LockSupport.unpark(threads[0]);
    }
}
```

```java
public static void main(String[] args) {
        SyncPark syncPark = new SyncPark(5);
        Thread t1 = new Thread(() -> {
            syncPark.print("a");
        });
        Thread t2 = new Thread(() -> {
            syncPark.print("b");
        });
        Thread t3 = new Thread(() -> {
            syncPark.print("c\n");
        });
        syncPark.setThreads(t1, t2, t3);
        syncPark.start();
    }
```

## 4 常见面试题总结
### 4.1 synchronized 关键字
#### 4.1.1 说一说自己对于 synchronized 关键字的了解
synchronized关键字解决的是多个线程之间访问资源的同步性，synchronized关键字可以保证被它修饰的方法或者代码块在任意时刻只能有一个线程执行。

另外，在 Java 早期版本中，synchronized属于重量级锁，效率低下，因为监视器锁（monitor）是依赖于底层的操作系统的 Mutex Lock 来实现的，Java 的线程是映射到操作系统的原生线程之上的。如果要挂起或者唤醒一个线程，都需要操作系统帮忙完成，而操作系统实现线程之间的切换时需要从用户态转换到内核态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高，这也是为什么早期的 synchronized 效率低的原因。庆幸的是在 Java 6 之后 Java 官方对从 JVM 层面对synchronized 较大优化，所以现在的 synchronized 锁效率也优化得很不错了。JDK1.6对锁的实现引入了大量的优化，如自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量级锁等技术来减少锁操作的开销。

#### 4.1.2 说说自己是怎么使用 synchronized 关键字，在项目中用到了吗
synchronized关键字最主要的三种使用方式：

1. 修饰实例方法: 作用于当前对象实例加锁，进入同步代码前要获得当前对象实例的锁
2. 修饰静态方法: 也就是给当前类加锁，会作用于类的所有对象实例，因为静态成员不属于任何一个实例对象，是类成员（ static 表明这是该类的一个静态资源，不管new了多少个对象，只有一份）。所以如果一个线程 A 调用一个实例对象的非静态 synchronized 方法，而线程 B 需要调用这个实例对象所属类的静态 synchronized 方法，是允许的，不会发生互斥现象，因为访问静态 synchronized 方法占用的锁是当前类的锁，而访问非静态 synchronized 方法占用的锁是当前实例对象锁。
3. 修饰代码块: 指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。

总结： synchronized 关键字加到 static 静态方法和 synchronized(class)代码块上都是是给 Class 类上锁。synchronized 关键字加到实例方法上是给对象实例上锁。尽量不要使用 synchronized(String a) 因为JVM中，字符串常量池具有缓存功能！

单例模式实现

单例模式：单例模式在内存中永远只有一个对象。单例模式就是说系统中对于某类的只能有一个对象，不可能出来第二个。
```java
//双重校验锁实现
public class Singleton {

    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    public static Singleton getUniqueInstance() {
       //先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (uniqueInstance == null) {
            //类对象加锁
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}

//使用静态内部类来实现
public class Singleton {
    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    public static Singleton getSingle() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                System.out.println(Singleton.getSingle());
            });
        }

        Arrays.asList(ths).forEach(o -> o.start());
    }
}
```

另外，需要注意 uniqueInstance 采用 volatile 关键字修饰也是很有必要。

uniqueInstance 采用 volatile 关键字修饰也是很有必要的， uniqueInstance = new Singleton(); 这段代码其实是分为三步执行：

1. 为 uniqueInstance 分配内存空间
2.  初始化 uniqueInstance
3. 将 uniqueInstance 指向分配的内存地址
   
但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。

使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。

#### 4.1.3 讲一下 synchronized 关键字的底层原理
synchronized 关键字底层原理属于 JVM 层面。

1. synchronized 同步语句块的情况
```java
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }
}
```
synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。 当执行 monitorenter 指令时，线程试图获取锁也就是获取 monitor(monitor对象存在于每个Java对象的对象头中，synchronized 锁便是通过这种方式获取锁的，也是为什么Java中任意对象可以作为锁的原因) 的持有权。当计数器为0则可以成功获取，获取后将锁计数器设为1也就是加1。相应的在执行 monitorexit 指令后，将锁计数器设为0，表明锁被释放。如果获取对象锁失败，那当前线程就要阻塞等待，直到锁被另外一个线程释放为止。

2. synchronized 修饰方法的的情况
synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 ACC_SYNCHRONIZED 标识，该标识指明了该方法是一个同步方法，JVM 通过该 ACC_SYNCHRONIZED 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。

#### 4.1.4 说说 JDK1.6 之后的synchronized 关键字底层做了哪些优化，可以详细介绍一下这些优化吗

JDK1.6 对锁的实现引入了大量的优化，如偏向锁、轻量级锁、自旋锁、适应性自旋锁、锁消除、锁粗化等技术来减少锁操作的开销。

锁主要存在四种状态，依次是：无锁状态、偏向锁状态、轻量级锁状态、重量级锁状态，他们会随着竞争的激烈而逐渐升级。注意锁可以升级不可降级，这种策略是为了提高获得锁和释放锁的效率。

[参考文章](https://gitee.com/SnailClimb/JavaGuide/blob/master/docs/java/Multithread/synchronized.md)

#### 4.1.5 谈谈 synchronized和ReentrantLock 的区别
1. 两者都是可重入锁

两者都是可重入锁。“可重入锁”概念是：自己可以再次获取自己的内部锁。比如一个线程获得了某个对象的锁，此时这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候还是可以获取的，如果不可锁重入的话，就会造成死锁。同一个线程每次获取锁，锁的计数器都自增1，所以要等到锁的计数器下降为0时才能释放锁。

2. synchronized 依赖于 JVM 而 ReentrantLock 依赖于 API

synchronized 是依赖于 JVM 实现的，前面我们也讲到了 虚拟机团队在 JDK1.6 为 synchronized 关键字进行了很多优化，但是这些优化都是在虚拟机层面实现的，并没有直接暴露给我们。ReentrantLock 是 JDK 层面实现的（也就是 API 层面，需要 lock() 和 unlock() 方法配合 try/finally 语句块来完成），所以我们可以通过查看它的源代码，来看它是如何实现的。

3. ReentrantLock 比 synchronized 增加了一些高级功能

相比synchronized，ReentrantLock增加了一些高级功能。主要来说主要有三点：①等待可中断；②可实现公平锁；③可实现选择性通知（锁可以绑定多个条件）

* ReentrantLock**提供了一种能够中断等待锁的线程的机制**，通过lock.lockInterruptibly()来实现这个机制。也就是说正在等待的线程可以选择放弃等待，改为处理其他事情。
* ReentrantLock**可以指定是公平锁还是非公平锁**。**而synchronized只能是非公平锁**。**所谓的公平锁就是先等待的线程先获得锁**。 ReentrantLock默认情况是非公平的，可以通过 ReentrantLock类的ReentrantLock(boolean fair)构造方法来制定是否是公平的。
* synchronized关键字与wait()和notify()/notifyAll()方法相结合可以实现等待/通知机制，ReentrantLock类当然也可以实现，但是需要借助于Condition接口与newCondition() 方法。Condition是JDK1.5之后才有的，它具有很好的灵活性，比如可以实现多路通知功能也就是在一个Lock对象中可以创建多个Condition实例（即对象监视器），**线程对象可以注册在指定的Condition中，从而可以有选择性的进行线程通知，在调度线程上更加灵活。** **在使用notify()/notifyAll()方法进行通知时，被通知的线程是由 JVM 选择的**，**用ReentrantLock类结合Condition实例可以实现“选择性通知”** ，这个功能非常重要，而且是Condition接口默认提供的。而synchronized关键字就相当于整个Lock对象中只有一个Condition实例，所有的线程都注册在它一个身上。如果执行notifyAll()方法的话就会通知所有处于等待状态的线程这样会造成很大的效率问题，而Condition实例的signalAll()方法 只会唤醒注册在该Condition实例中的所有等待线程。

如果你想使用上述功能，那么选择ReentrantLock是一个不错的选择。

4. 性能已不是选择标准