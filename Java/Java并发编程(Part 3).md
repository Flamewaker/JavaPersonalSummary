# Java并发编程(Part 3)
## 1. Java内存模型
JMM 即 Java Memory Model，它定义了主存、工作内存抽象概念，底层对应着 CPU 寄存器、缓存、硬件内存、CPU 指令优化等。
JMM 体现在以下几个方面
* 原子性 - 保证指令不会受到线程上下文切换的影响。
* 可见性 - 保证指令不会受 cpu 缓存的影响。(避免从缓存中读取最新值)
* 有序性 - 保证指令不会受 cpu 指令并行优化的影响。 

### 1.1 volatile 
volatile（易变关键字）
它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，线程操作 volatile 变量都是直接操作主存。它保证的是在多个线程之间，**一个线程对 volatile 变量的修改**对另一个线程可见，不能保证原子性，仅用在一个写线程，多个读线程的情况。

如果一个变量被volatile修饰了，那么肯定可以保证每次读取这个变量值的时候得到的值是最新的，但是一旦需要对变量进行自增这样的非原子操作，就不会保证这个变量的原子性了。（一个变量i被volatile修饰，两个线程想对这个变量修改，都对其进行自增操作也就是i++，i++的过程可以分为三步，首先获取i的值，其次对i的值进行加1，最后将得到的新值写会到缓存中。首先线程A读取了i的变量的值，这个时候线程切换到了B，线程B同样从主内存中读取i的值，由于线程A没有对i做过任何修改，此时线程B获取到的i仍然是100。线程B工作内存中为i执行了加1的操作，但是没有刷新到主内存中，这个时候又切换到了A线程，A线程直接对工作内存中的100进行加1操作（因为A线程已经读取过i的值了），由于线程B并未写入i的最新值，这个时候A线程的工内存中的100不会失效。 最后，线程A将i=101写入主内存中，线程B也将i=101写入主内存中。）（解释：自增操作不具备原子性，用volatile修饰i并不能使操作具有原子性，但是使用synchronized是可以的。）

注意 synchronized 语句块既可以保证代码块的原子性，也同时保证代码块内变量的可见性。但缺点是 synchronized 是属于重量级操作，性能相对更低。

当一个变量被声明为 volatile 时，线程在写入变量时不会将值缓存在寄存器中或其他地方，而是会会把值直接刷新回主内存。

System.out.println() 能保证可见性的原因是，println()内部由**synchronized**上锁，保证了可见性。
```java
public void println(Object x) {
    String s = String.valueOf(x);
    synchronized (this) {            
        print(s);
        newLine();
    }
}
```

### 1.2 有序性
JVM 会在不影响正确性的前提下，可以调整语句的执行顺序。但在多线程下会影响正确性。
volatile 修饰的变量，可以禁用指令重排。

### 1.3 两阶段终止模式 volatile
每隔一秒进行监控。
加入 Balking （犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，那么本线程就无需再做
了，直接结束返回。
```java
public class TwoPhaseTermination {

    public static void main(String[] args) {
        TPTVolatile t = new TPTVolatile();
        t.start();
        t.start();
//        try {
//            Thread.sleep(3500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("停止监控");
//        t.stop();
    }

}

class TPTVolatile {
    private Thread thread;
    private volatile boolean stop = false;
    
    //判断是否执行过监控方法(犹豫模式balking)
    
    private volatile boolean starting = false;

    public void start() {
        //加入犹豫模式balking(这里类似单例模式，由于只需要一个监控线程进行监控)
        
        synchronized (this) {
            if (starting) {
                return;
            }
            starting = true;
        }
        
        //监控方法

        thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    System.out.println("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("进行监控操作");
                } catch (InterruptedException e) {

                }

                //System.out.println("进行监控操作");
            }

            System.out.println("监控线程停止");
            starting = false; //volatile 保证可见性
        }, "监控线程");

        stop = false;
        thread.start();
    }

    public void stop() {
        stop = true;
        //如果设置为真，但是线程还在sleep的过程中，直接打断sleep过程，报出InterruptedException，此时直接进行下一次循环，获得stop值
        //从而尽快结束线程。
        thread.interrupt();
    }
}
```

### 1.4 内存屏障
* 可见性
   * 写屏障（sfence）保证在该屏障之前的，对共享变量的改动，都同步到主存当中
   * 读屏障（lfence）保证在该屏障之后，对共享变量的读取，加载的是主存中最新数据
* 有序性
   * 写屏障会确保指令重排序时，不会将写屏障之前的代码排在写屏障之后
   * 读屏障会确保指令重排序时，不会将读屏障之后的代码排在读屏障之前

### 1.5 volatile原理
volatile 的底层实现原理是内存屏障，Memory Barrier（Memory Fence）
* 对 volatile 变量的写指令后会加入写屏障
* 对 volatile 变量的读指令前会加入读屏障

#### 1.5.1 保证可见性
* 写屏障（sfence）保证在该屏障之前的，对共享变量的改动，都同步到主存当中
* 读屏障（lfence）保证在该屏障之后，对共享变量的读取，加载的是主存中最新数据

#### 1.5.2 保证有序性
* 写屏障会确保指令重排序时，不会将写屏障之前的代码排在写屏障之后
* 读屏障会确保指令重排序时，不会将读屏障之后的代码排在读屏障之前

不能解决指令交错:
* 写屏障仅仅是保证之后的读能够读到最新的结果，但不能保证读跑到它前面去
* 而有序性的保证也只是保证了本线程内相关代码不被重排序

#### 1.5.3 double-checked locking 单例模式问题  
```java
public final class Singleton {
    private Singleton() { }
    private static Singleton INSTANCE = null;
    public static Singleton getInstance() {
        synchronized(Singleton.class) {
            if (INSTANCE == null) { // t1
                INSTANCE = new Singleton();
            }
            return INSTANCE;
        }
    }
}

```
锁粒度比较大，我需要首次使用 getInstance() 才使用 synchronized 加锁，后续使用时无需加锁。于是改成如下代码。
```java
public final class Singleton {
    private Singleton() { }
    private static volatile Singleton INSTANCE = null;
    public static Singleton getInstance() {
        if(INSTANCE == null) { // t2
        // 首次访问会同步，而之后的使用没有 synchronized
            synchronized(Singleton.class) {
                if (INSTANCE == null) { // t1
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
```
很关键的一点：第一个 if 使用了 INSTANCE 变量，是在同步块之外。无法保证外面的INSTANCE的原子性与可见性。于是需要volatile保证原子性与可见性。

如果没有加 volatile。
```java
INSTANCE = new Singleton();
```
反汇编后代码解释如下：
17 表示创建对象，将对象引用入栈 // new Singleton
20 表示复制一份对象引用 // 引用地址
21 表示利用一个对象引用，调用构造方法
24 表示利用一个对象引用，赋值给 static INSTANCE

24 和 21 在 synchronized， 有可能发生指令重排序，赋值了引用 (对象非空了)，但还没有调用构造方法，另一个线程t2，访问外面的INSTANCE 时，此时会判断到对象非空，这时 t1 还未完全将构造方法执行完毕，如果在构造方法中要执行很多初始化操作，那么 t2 拿到的是将是一个未初始化完毕的单例。对 INSTANCE 使用 volatile 修饰即可，可以禁用指令重排。volatile加上之后，24之后加入写屏障，写屏障会确保指令重排序时，不会将写屏障之前的代码排在写屏障之后。

#### 1.5.4 
happens-before 规定了对共享变量的写操作对其它线程的读操作可见，它是可见性与有序性的一套规则总结，抛
开以下 happens-before 规则，JMM 并不能保证一个线程对共享变量的写，对于其它线程对该共享变量的读可见。
* 线程解锁之前对变量的写，对于接下来对加锁的其它线程对该变量的读可见。(锁后写操作执行完毕，得到锁的读操作的得到是改变后的变量)
* 线程对 volatile 变量的写，对接下来其它线程对该变量的读可见。(读可以看到最新的结果)
* 线程 start 前对变量的写，对该线程开始后对该变量的读可见。
* 线程结束前对变量的写，对其它线程得知它结束后的读可见（比如其它线程调用 t1.isAlive() 或 t1.join()等待
它结束）
* 线程 t1 打断 t2（interrupt）前对变量的写，对于其他线程得知 t2 被打断后对变量的读可见（通过
t2.interrupted 或 t2.isInterrupted） 
* 对变量默认值（0，false，null）的写，对其它线程对该变量的读可见。
* 具有传递性，如果 x hb-> y 并且 y hb-> z 那么有 x hb-> z ，配合 volatile 的防指令重排

## 2. volatile关键字面试题
### 2.1. 讲一下Java内存模型
在 JDK1.2 之前，Java的内存模型实现总是从主存（即共享内存）读取变量，是不需要进行特别的注意的。而在当前的 Java 内存模型下，线程可以把变量保存本地内存（比如机器的寄存器）中，而不是直接在主存中进行读写。这就可能造成一个线程在主存中修改了一个变量的值，而另外一个线程还继续使用它在寄存器中的变量值的拷贝，造成数据的不一致。

要解决这个问题，就需要把变量声明为volatile，这就指示 JVM，这个变量是不稳定的，每次使用它都到主存中进行读取。

说白了， volatile 关键字的主要作用就是保证变量的可见性然后还有一个作用是防止指令重排序。

### 2.2 2.2 并发编程的三个重要特性
* 原子性 : 一个的操作或者多次操作，要么所有的操作全部都得到执行并且不会收到任何因素的干扰而中断，要么所有的操作都执行，要么都不执行。synchronized 可以保证代码片段的原子性。
* 可见性 ：当一个变量对共享变量进行了修改，那么另外的线程都是立即可以看到修改后的最新值。volatile 关键字可以保证共享变量的可见性。
* 有序性 ：代码在执行的过程中的先后顺序，Java 在编译器以及运行期间的优化，代码的执行顺序未必就是编写代码时候的顺序。volatile 关键字可以禁止指令进行重排序优化。

### 2.3. 说说 synchronized 关键字和 volatile 关键字的区别
synchronized 关键字和 volatile 关键字是两个互补的存在，而不是对立的存在：

* volatile关键字是线程同步的轻量级实现，所以volatile性能肯定比synchronized关键字要好。但是volatile关键字只能用于变量而synchronized关键字可以修饰方法以及代码块。synchronized关键字在JavaSE1.6之后进行了主要包括为了减少获得锁和释放锁带来的性能消耗而引入的偏向锁和轻量级锁以及其它各种优化之后执行效率有了显著提升，实际开发中使用 synchronized 关键字的场景还是更多一些。
* 多线程访问volatile关键字不会发生阻塞，而synchronized关键字可能会发生阻塞
* volatile关键字能保证数据的可见性，但不能保证数据的原子性。synchronized关键字两者都能保证。
* volatile关键字主要用于解决变量在多个线程之间的可见性，而 synchronized关键字解决的是多个线程之间访问资源的同步性。