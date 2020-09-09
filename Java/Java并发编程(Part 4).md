# Java并发编程(Part 4)

## 1. 共享模型之无锁
### 1.1 CAS 比较并设置 (也有 Compare And Swap 的说法) 
compareAndSet 在 set 前，先比较 prev 与当前值:
- 不一致了，next 作废，返回 false 表示失败。比如，别的线程已经做了减法，当前值已经被减成了 990。那么本线程的这次 990 就作废了，进入 while 下次循环重试。
- 一致，以 next 设置为新值，返回 true 表示成功。

其实 CAS 的底层是 lock cmpxchg 指令（X86 架构），在单核 CPU 和多核 CPU 下都能够保证(比较-交换)的原子性。(原子操作)

CAS 需要 volatile 来支持。CAS 必须借助 volatile 才能读取到共享变量的最新值来实现比较并交换的效果。变量用 volatile 修饰。

```java
AtomicInteger balance = new AtomicInteger();
while (true) {
    // 比如拿到了旧值 1000
    int prev = balance.get();
    // 在这个基础上 1000-10 = 990
    int next = prev - amount;
    if (balance.compareAndSet(prev, next)) {
        break;
    }
}

do {
    prev = balance.get();
    next = prev - amount;
} while (!compareAndSet(prev, next));
// =>
balance.addAndGet(-1 * amount);
```

无锁效率高: 无锁情况下，即使重试失败，线程始终在高速运行，没有停歇，而 synchronized 会让线程在没有获得锁的时候，发生上下文切换，进入阻塞。但线程数太多了(多于核数)效率不一定高。

CAS 的特点： 
结合 CAS 和 volatile 可以实现无锁并发，适用于线程数少、多核 CPU 的场景下。

CAS 是基于**乐观锁**的思想：最乐观的估计，不怕别的线程来修改共享变量，就算改了也没关系，我吃亏点再重试呗。

synchronized 是基于**悲观锁**的思想：最悲观的估计，得防着其它线程来修改共享变量，我上了锁你们都别想改，我改完了解开锁，你们才有机会。

CAS 体现的是无锁并发、无阻塞并发，请仔细体会这两句话的意思。
* 因为没有使用 synchronized，所以线程不会陷入阻塞，这是效率提升的因素之一。
*  但如果竞争激烈，可以想到重试必然频繁发生，反而效率会受影响


### 1.2 原子类型
J.U.C 并发包提供了原子整数：
* AtomicBoolean
* AtomicInteger
* AtomicLong

J.U.C 并发包提供了原子引用：
* AtomicReference
* AtomicMarkableReference
* AtomicStampedReference
```
AtomicReference<BigDecimal> ref;
```

J.U.C 并发包提供了原子数组：
* AtomicIntegerArray
* AtomicLongArray
* AtomicReferenceArray

J.U.C 并发包提供了字段更新器
* AtomicReferenceFieldUpdater // 域 字段
* AtomicIntegerFieldUpdater
* AtomicLongFieldUpdater

利用字段更新器，可以针对对象的某个域（Field）进行原子操作，只能配合 volatile 修饰的字段使用，否则会出现异常。

ABA问题：
```java
static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
public static void main(String[] args) throws InterruptedException {
    System.out.println("main start...");
    // 获取值 A
    String prev = ref.getReference();
    // 获取版本号
    int stamp = ref.getStamp();
    System.out.println("版本 " + stamp);
    // 如果中间有其它线程干扰，发生了 ABA 现象
    other();
    sleep(3);
    // 尝试改为 C
    System.out.println("change A->C " + ref.compareAndSet(prev, "C", stamp, stamp + 1));
}

private static void other() throws InterruptedException {
    new Thread(() -> {
        System.out.println("change A->B " + ref.compareAndSet(ref.getReference(), "B", ref.getStamp(), ref.getStamp() + 1));
        System.out.println("更新版本为 " +  ref.getStamp());
    }, "t1").start();
    sleep(1);        
    new Thread(() -> {
        System.out.println("change B->A " + ref.compareAndSet(ref.getReference(), "A", ref.getStamp(), ref.getStamp() + 1));
        System.out.println("更新版本为 " + ref.getStamp());
    }, "t2").start();
}
```
```java
main start...
版本 0
change A->B true
更新版本为 1
change B->A true
更新版本为 2
change A->C false
```

## 2. 共享模型之不可变
final 的使用(String 类、类中所有属性都是 final)
* 属性用 final 修饰保证了该属性是只读的，不能修改
* 类用 final 修饰保证了该类中的方法不能被覆盖，防止子类无意间破坏不可变性

### 2.1 保护性拷贝
通过创建副本对象来避免共享的手段称之为保护性拷贝（defensive copy）, 创建副本免于直接引用之前的对象，而保证安全性。

### 2.2 享元模式
定义 英文名称：Flyweight pattern. 当需要重用数量有限的同一类对象时。
A flyweight is an object that minimizes memory usage by sharing as much data as
possible with other similar objects
#### 2.2.1 体现
在JDK中 Boolean，Byte，Short，Integer，Long，Character 等包装类提供了 valueOf 方法，例如 Long 的 valueOf 会缓存 -128~127 之间的 Long 对象，在这个范围之间会重用对象，大于这个范围，才会新建 Long 对象：
```java
public static Long valueOf(long l) {
    final int offset = 128;
    if (l >= -128 && l <= 127) { // will cache
        return LongCache.cache[(int)l + offset];
    }
    return new Long(l);
}
```

* Byte, Short, Long 缓存的范围都是 -128~127
* Character 缓存的范围是 0~127
* Integer的默认范围是 -128~127, 最小值不能变, 但最大值可以通过调整虚拟机参数,
-Djava.lang.Integer.IntegerCache.high` 来改变
* Boolean 缓存了 TRUE 和 FALSE

### 2.3 final的原理
设置 final 变量的原理
```java
public class TestFinal {
    final int a = 20;
}
```
字节码
```java
0: aload_0
1: invokespecial #1 // Method java/lang/Object."<init>":()V
4: aload_0
5: bipush 20
7: putfield #2 // Field a:I
<-- 写屏障
10: return
```
final 变量的赋值也会通过 putfield 指令来完成，同样在这条指令之后也会加入写屏障，保证在其它线程读到
它的值时不会出现为 0 的情况

### 2.4 无状态
在 web 阶段学习时，设计 Servlet 时为了保证其线程安全，都会有这样的建议，不要为 Servlet 设置成员变量，这种没有任何成员变量的类是线程安全的。

因为成员变量保存的数据也可以称为状态信息，因此没有成员变量就称之为**无状态**

## 3. Atomic 原子类面试题
### 3.1 介绍一下Atomic 原子类
Atomic 是指一个操作是不可中断的。即使是在多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。所以，所谓原子类说简单点就是具有原子/原子操作特征的类。

### 3.2 JUC 包中的原子类是哪4类?
基本类型

使用原子的方式更新基本类型

* AtomicInteger：整形原子类
* AtomicLong：长整型原子类
* AtomicBoolean：布尔型原子类

数组类型

使用原子的方式更新数组里的某个元素

* AtomicIntegerArray：整形数组原子类
* AtomicLongArray：长整形数组原子类
* AtomicReferenceArray：引用类型数组原子类

引用类型

* AtomicReference：引用类型原子类
* AtomicStampedReference：原子更新引用类型里的字段原子类
* AtomicMarkableReference ：原子更新带有标记位的引用类型
  
对象的属性修改类型

* AtomicIntegerFieldUpdater：原子更新整形字段的更新器
* AtomicLongFieldUpdater：原子更新长整形字段的更新器
* AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于解决原子的更新数据和数据的版本号，可以解决使用 CAS 进行原子更新时可能出现的 ABA 问题。

### 3.3 讲讲 AtomicInteger 的使用
AtomicInteger 类常用方法
```
public final int get() //获取当前的值
public final int getAndSet(int newValue)//获取当前的值，并设置新的值
public final int getAndIncrement()//获取当前的值，并自增
public final int getAndDecrement() //获取当前的值，并自减
public final int getAndAdd(int delta) //获取当前的值，并加上预期的值
boolean compareAndSet(int expect, int update) //如果输入的数值等于预期值，则以原子方式将该值设置为输入值（update）
public final void lazySet(int newValue)//最终设置为newValue,使用 lazySet 设置之后可能导致其他线程在之后的一小段时间内还是可以读到旧的值。
```
使用 AtomicInteger 之后，不用对 incrementAndGet() 方法加锁也可以保证线程安全。

### 3.4 能不能给我简单介绍一下 AtomicInteger 类的原理
AtomicInteger 线程安全原理简单分析

AtomicInteger 类的部分源码：

```java
    // setup to use Unsafe.compareAndSwapInt for updates（更新操作时提供“比较并替换”的作用）
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;
```

AtomicInteger 类主要利用 CAS (compare and swap) + volatile 和 native 方法来保证原子操作，从而避免 synchronized 的高开销，执行效率大为提升。

CAS的原理是拿期望的值和原本的一个值作比较，如果相同则更新成新的值。UnSafe 类的 objectFieldOffset() 方法是一个本地方法，这个方法是用来拿到“原来的值”的内存地址，返回值是 valueOffset。另外 value 是一个volatile变量，在内存中可见，因此 JVM 可以保证任何时刻任何线程总能拿到该变量的最新值。

## 单例模式
饿汉式：类加载就会导致该单实例对象被创建
```java
// 问题1：为什么加 final: 防止继承的子类改变单例
// 问题2：如果实现了序列化接口, 还要做什么来防止反序列化破坏单例
public final class Singleton implements Serializable {
    // 问题3：为什么设置为私有? 是否能防止反射创建新的实例? : 不能
    private Singleton() {}
    // 问题4：这样初始化是否能保证单例对象创建时的线程安全? : 静态对象的初始化是在类加载时完成的。
    private static final Singleton INSTANCE = new Singleton();
    // 问题5：为什么提供静态方法而不是直接将 INSTANCE 设置为 public, 说出你知道的理由 : 提供更好的封装性
    public static Singleton getInstance() {
        return INSTANCE;
    }
    public Object readResolve() {
        return INSTANCE;
    }
}
```

懒汉式：类加载不会导致该单实例对象被创建，而是首次使用该对象时才会创建
```java
public final class Singleton {
    private Singleton() { }
    private static Singleton INSTANCE = null;
    // 分析这里的线程安全, 并说明有什么缺点 : 锁粒度较大，每次都要加锁
    public static synchronized Singleton getInstance() {
        if( INSTANCE != null ){
            return INSTANCE;
        }
        INSTANCE = new Singleton();
        return INSTANCE;
    }
}
```

```java
public final class Singleton {
    private Singleton() { }
    // 问题1：解释为什么要加 volatile ? : 保证指令不发生重排序，防止另一个线程拿到未初始化好的对象
    private static volatile Singleton INSTANCE = null;
    // 问题2：对比上面的实现, 说出这样做的意义 : 当不为空的时候，可以直接返回而不会加锁。
    public static Singleton getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (Singleton.class) {
            // 问题3：为什么还要在这里加为空判断, 之前不是判断过了吗 : 防止首次创建对象发生的多线程存在的问题，防止多个创建。
            if (INSTANCE != null) { // t2
                return INSTANCE;
            }
            INSTANCE = new Singleton();
            return INSTANCE;
        }
    }
}
```

```java
public final class Singleton {
    private Singleton() { }
    // 问题1：属于懒汉式 :  类总是在第一次用到时才会执行类加载操作。
    private static class LazyHolder {
        static final Singleton INSTANCE = new Singleton();
    }
    // 问题2：在创建时是否有并发问题 : 类加载由JVM保证线程安全性。
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```
