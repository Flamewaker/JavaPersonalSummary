# HashMap 和 ConcurrentHashMap 某些面试问题补充

## 1. 为什么 ConcurrentHashMap 的读操作不需要加锁？

> ConcurrentHashMap 在jdk1.7中是采用Segment + HashEntry + ReentrantLock的方式进行实现的，而1.8中放弃了Segment臃肿的设计，取而代之的是采用Node + CAS + Synchronized来保证并发安全进行实现。

- JDK1.8的实现降低锁的粒度，JDK1.7版本锁的粒度是基于Segment的，包含多个HashEntry，而JDK1.8锁的粒度就是HashEntry（首节点）
- JDK1.8版本的数据结构变得更加简单，使得操作也更加清晰流畅，因为已经使用synchronized来进行同步，所以不需要分段锁的概念，也就不需要Segment这种数据结构了，由于粒度的降低，实现的复杂度也增加了
- JDK1.8使用红黑树来优化链表，基于长度很长的链表的遍历是一个很漫长的过程，而红黑树的遍历效率是很快的，代替一定阈值的链表，这样形成一个最佳拍档。

### **get操作源码**

- 首先计算hash值，定位到该table索引位置，如果是首节点符合就返回
- 如果遇到扩容的时候，会调用标志正在扩容节点ForwardingNode的find方法，查找该节点，匹配就返回
- 以上都不符合的话，就往下遍历节点，匹配就返回，否则最后就返回null

```java
//会发现源码中没有一处加了锁
public V get(Object key) {
    Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
    int h = spread(key.hashCode()); //计算hash
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (e = tabAt(tab, (n - 1) & h)) != null) {//读取首节点的Node元素
        if ((eh = e.hash) == h) { //如果该节点就是首节点就返回
            if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                return e.val;
        }
        //hash值为负值表示正在扩容，这个时候查的是ForwardingNode的find方法来定位到nextTable来
        //eh=-1，说明该节点是一个ForwardingNode，正在迁移，此时调用ForwardingNode的find方法去nextTable里找。
        //eh=-2，说明该节点是一个TreeBin，此时调用TreeBin的find方法遍历红黑树，由于红黑树有可能正在旋转变色，所以find里会有读写锁。
        //eh>=0，说明该节点下挂的是一个链表，直接遍历该链表即可。
        else if (eh < 0)
            return (p = e.find(h, key)) != null ? p.val : null;
        while ((e = e.next) != null) {//既不是首节点也不是ForwardingNode，那就往下遍历
            if (e.hash == h &&
                ((ek = e.key) == key || (ek != null && key.equals(ek))))
                return e.val;
        }
    }
    return null;
}
```

1. 计算hash值，定位到该table索引位置，如果是首节点符合就返回
2. 如果遇到扩容的时候，会调用标志正在扩容节点ForwardingNode的find方法，查找该节点，匹配就返回
3. 以上都不符合的话，就往下遍历节点，匹配就返回，否则最后就返回null

### **volatile登场**

对于可见性，Java提供了volatile关键字来保证可见性、有序性。但不保证原子性。

普通的共享变量不能保证可见性，因为普通共享变量被修改之后，什么时候被写入主存是不确定的，当其他线程去读取时，此时内存中可能还是原来的旧值，因此无法保证可见性。

- volatile关键字对于基本类型的修改可以在随后对多个线程的读保持一致，但是对于引用类型如数组，实体bean，仅仅保证引用的可见性，但并不保证引用内容的可见性。。
- 禁止进行指令重排序。

背景：为了提高处理速度，处理器不直接和内存进行通信，而是先将系统内存的数据读到内部缓存（L1，L2或其他）后再进行操作，但操作完不知道何时会写到内存。

- 如果对声明了volatile的变量进行写操作，JVM就会向处理器发送一条指令，将这个变量所在缓存行的数据写回到系统内存。但是，就算写回到内存，如果其他处理器缓存的值还是旧的，再执行计算操作就会有问题。
- 在多处理器下，为了保证各个处理器的缓存是一致的，就会实现缓存一致性协议，当某个CPU在写数据时，如果发现操作的变量是共享变量，则会通知其他CPU告知该变量的缓存行是无效的，因此其他CPU在读取该变量时，发现其无效会重新从主存中加载数据。

**总结：定义成volatile的变量，能够在线程之间保持可见性，能够被多线程同时读，而不会读到过期的值。由于get操作中只需要读不需要写共享变量，所以可以不用加锁。之所以不会读到过期的值，依据Java内存模型的happen before原则，对volatile字段的写入操作先于读操作，get总能拿到最新的值。**

### **是加在数组上的volatile吗?**

```java
	/**
     * The array of bins. Lazily initialized upon first insertion.
     * Size is always a power of two. Accessed directly by iterators.
     */
    transient volatile Node<K,V>[] table;
```

我们知道volatile可以修饰数组的，只是意思和它表面上看起来的样子不同。举个栗子，volatile int array[10]是指array的地址是volatile的而不是数组元素的值是volatile的.

### **用volatile修饰的Node**

get操作可以无锁是由于Node的元素val和指针next是用volatile修饰的，在多线程环境下线程A修改结点的val或者新增节点的时候是对线程B可见的。

```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }

        /**
         * Virtualized support for map.get(); overridden in subclasses.
         */
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                        ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }
```

> 既然volatile修饰数组对get操作没有效果那加在数组上的volatile的目的是什么呢？

其实就是为了使得Node数组在扩容的时候对其他线程具有可见性而加的volatile。

- 在1.8中ConcurrentHashMap的get操作全程不需要加锁，这也是它比其他并发集合比如hashtable、用Collections.synchronizedMap()包装的hashmap;安全效率高的原因之一。
- get操作全程不需要加锁是因为Node的成员val是用volatile修饰的和数组用volatile修饰没有关系。
- 数组用volatile修饰主要是保证在数组扩容的时候保证可见性。

### **Put操作源码**

内部使用volatile来保证数据存储的可见性；

利用CAS操作，在特定场景下进行无锁并发操作，内部的锁实际用的是syncronized；

因为在jdk8中，syncronized已经得到性能的优化，并且对比再入锁可以减少内存消耗。

```java
public V put(K key, V value) {
    return putVal(key, value, false);
}
/** Implementation for put and putIfAbsent */
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    int hash = spread(key.hashCode()); //两次hash，减少hash冲突，可以均匀分布
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) { //对这个table进行迭代
        Node<K,V> f; int n, i, fh;
        //这里就是上面构造方法没有进行初始化，在这里进行判断，为null就调用initTable进行初始化，属于懒汉模式初始化
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {//如果i位置没有数据，就直接无锁插入
            if (casTabAt(tab, i, null,
                         new Node<K,V>(hash, key, value, null)))
                break;                   // no lock when adding to empty bin
        }
        else if ((fh = f.hash) == MOVED)//如果在进行扩容，则先进行扩容操作
            tab = helpTransfer(tab, f);
        else {
            V oldVal = null;
            //如果以上条件都不满足，那就要进行加锁操作，也就是存在hash冲突，锁住链表或者红黑树的头结点
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) { //表示该节点是链表结构
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            //这里涉及到相同的key进行put就会覆盖原先的value
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {  //插入链表尾部
                                pred.next = new Node<K,V>(hash, key,
                                                          value, null);
                                break;
                            }
                        }
                    }
                    else if (f instanceof TreeBin) {//红黑树结构
                        Node<K,V> p;
                        binCount = 2;
                        //红黑树结构旋转插入
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                       value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            if (binCount != 0) { //如果链表的长度大于8时就会进行红黑树的转换
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    addCount(1L, binCount);//统计size，并且检查是否需要扩容
    return null;
}
```

1. 如果没有初始化就先调用initTable（）方法来进行初始化过程
2. 如果没有hash冲突就直接CAS插入
3. 如果还在进行扩容操作就先进行扩容
4. 如果存在hash冲突，就加锁来保证线程安全，这里有两种情况，一种是链表形式就直接遍历到尾端插入，一种是红黑树就按照红黑树结构插入，
5. 最后一个如果该链表的数量大于阈值8，就要先转换成黑红树的结构，break再一次进入循环
6. 如果添加成功就调用addCount（）方法统计size，并且检查是否需要扩容

### **Size操作源码**

```java
public int size() {
    long n = sumCount();
    return ((n < 0L) ? 0 :
            (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
            (int)n);
}

public long mappingCount() {
        long n = sumCount();
        return (n < 0L) ? 0L : n; // ignore transient negative values
    }


final long sumCount() {
    CounterCell[] as = counterCells; CounterCell a; //变化的数量
    long sum = baseCount;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}

// 从 putVal 传入的参数是 1， binCount，binCount 默认是0，只有 hash 冲突了才会大于 1.且他的大小是链表的长度（如果不是红黑数结构的话）。
private final void addCount(long x, int check) {
    CounterCell[] as; long b, s;
    // 如果计数盒子不是空 或者
    // 如果修改 baseCount 失败
    if ((as = counterCells) != null ||
        !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
        CounterCell a; long v; int m;
        boolean uncontended = true;
        // 如果计数盒子是空（尚未出现并发）
        // 如果随机取余一个数组位置为空 或者
        // 修改这个槽位的变量失败（出现并发了）
        // 执行 fullAddCount 方法。并结束
        if (as == null || (m = as.length - 1) < 0 ||
            (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
            !(uncontended =
              U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
            fullAddCount(x, uncontended);
            return;
        }
        if (check <= 1)
            return;
        s = sumCount();
    }
    // 如果需要检查,检查是否需要扩容，在 putVal 方法调用时，默认就是要检查的。
    if (check >= 0) {
        Node<K,V>[] tab, nt; int n, sc;
        // 如果map.size() 大于 sizeCtl（达到扩容阈值需要扩容） 且
        // table 不是空；且 table 的长度小于 1 << 30。（可以扩容）
        while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
               (n = tab.length) < MAXIMUM_CAPACITY) {
            // 根据 length 得到一个标识
            int rs = resizeStamp(n);
            // 如果正在扩容
            if (sc < 0) {
                // 如果 sc 的低 16 位不等于 标识符（校验异常 sizeCtl 变化了）
                // 如果 sc == 标识符 + 1 （扩容结束了，不再有线程进行扩容）（默认第一个线程设置 sc ==rs 左移 16 位 + 2，当第一个线程结束扩容了，就会将 sc 减一。这个时候，sc 就等于 rs + 1）
                // 如果 sc == 标识符 + 65535（帮助线程数已经达到最大）
                // 如果 nextTable == null（结束扩容了）
                // 如果 transferIndex <= 0 (转移状态变化了)
                // 结束循环 
                if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                    sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                    transferIndex <= 0)
                    break;
                // 如果可以帮助扩容，那么将 sc 加 1. 表示多了一个线程在帮助扩容
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                    // 扩容
                    transfer(tab, nt);
            }
            // 如果不在扩容，将 sc 更新：标识符左移 16 位 然后 + 2. 也就是变成一个负数。高 16 位是标识符，低 16 位初始是 2.
            else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                         (rs << RESIZE_STAMP_SHIFT) + 2))
                // 更新 sizeCtl 为负数后，开始扩容。
                transfer(tab, null);
            s = sumCount();
        }
    }
}
```

先利用`sumCount()`计算，然后如果值超过int的最大值，就返回int的最大值。但是有时size就会超过最大值，这时最好用`mappingCount`方法。sumCount有两个重要的属性`baseCount`和`counterCells`,如果`counterCells`不为空，那么总共的大小就是baseCount与遍历`counterCells`的value值累加获得的。

```java
/**
* Base counter value, used mainly when there is no contention,
* but also as a fallback during table initialization
* races. Updated via CAS.
* 当没有争用时，使用这个变量计数。
*/
private transient volatile long baseCount;

// 一种用于分配计数的填充单元。改编自LongAdder和Striped64。请查看他们的内部文档进行解释。
@sun.misc.Contended 
static final class CounterCell {
    volatile long value;
    CounterCell(long x) { value = x; }
}
//缓存系统中是以缓存行（cache line）为单位存储的。缓存行是2的整数幂个连续字节，一般为32-256个字节。最常见的缓存行大小是64个字节。当多线程修改互相独立的变量时，如果这些变量共享同一个缓存行，就会无意中影响彼此的性能，这就是伪共享。
//避免伪共享(false sharing)。。
```

1. 如果 counterCells == null, 则对 baseCount 做 CAS 自增操作。

2. 如果并发导致 baseCount CAS 失败了使用 counterCells。

3. 如果counterCells CAS 失败了，在 fullAddCount 方法中，会继续死循环操作，直到成功。

总结：

1. 对 table 的长度加一。无论是通过修改 baseCount，还是通过使用 CounterCell。当 CounterCell 被初始化了，就优先使用他，不再使用 baseCount。

2. 检查是否需要扩容，或者是否正在扩容。如果需要扩容，就调用扩容方法，如果正在扩容，就帮助其扩容。

3. 在没有并发的情况下，使用一个 baseCount volatile 变量就足够了，当并发的时候，CAS 修改 baseCount 失败后，就会使用 CounterCell 类了，会创建一个这个对象，通常对象的 volatile value 属性是 1。在计算 size 的时候，会将 baseCount 和 CounterCell 数组中的元素的 value 累加，得到总的大小，但这个数字仍旧可能是不准确的。

4. 还有一个需要注意的地方就是，这个 CounterCell 类使用了 @sun.misc.Contended 注解标识，这个注解是防止伪共享的。



## 2. 你知道 hash 的实现吗？为什么要这样实现？为什么要用异或运算符？

JDK 1.8 中，是通过 hashCode() 的高 16 位异或低 16 位实现的：(h = k.hashCode()) ^ (h >>> 16)，主要是从速度，功效和质量来考虑的，减少系统的开销，也不会造成因为高位没有参与下标的计算，从而引起的碰撞。

保证了对象的 hashCode 的 32 位值只要有一位发生改变，整个 hash() 返回值就会改变。尽可能的减少碰撞。

## 3. 拉链法导致的链表过深问题为什么不用二叉查找树代替，而选择红黑树？为什么不一直使用红黑树？

之所以选择红黑树是为了解决二叉查找树的缺陷，二叉查找树在特殊情况下会变成一条线性结构（这就跟原来使用链表结构一样了，造成很深的问题），遍历查找会非常慢。

而红黑树在插入新数据后可能需要通过左旋，右旋、变色这些操作来保持平衡，引入红黑树就是为了查找数据快，解决链表查询深度的问题，我们知道红黑树属于平衡二叉树，但是为了保持“平衡”是需要付出代价的，但是该代价所损耗的资源要比遍历线性链表要少，所以当长度大于8的时候，会使用红黑树，如果链表长度很短的话，根本不需要引入红黑树，引入反而会慢。

红黑树：每个节点非红即黑。根节点总是黑色的如果节点是红色的，则它的子节点必须是黑色的（反之不一定）。每个叶子节点都是黑色的空节点（NIL节点）。从根节点到叶节点或空子节点的每条路径，必须包含相同数目的黑色节点（即相同的黑色高度）。

## 4. HashMap，LinkedHashMap，TreeMap 有什么区别？HashMap ，TreeMap，LinkedHashMap 使用场景？

LinkedHashMap 保存了记录的插入顺序，在用 Iterator 遍历时，先取到的记录肯定是先插入的；遍历比 HashMap 慢；

TreeMap 实现 SortMap 接口，能够把它保存的记录根据键排序（默认按键值升序排序，也可以指定排序的比较器）

一般情况下，使用最多的是 HashMap。

HashMap：在 Map 中插入、删除和定位元素时；

TreeMap：在需要按自然顺序或自定义顺序遍历键的情况下；

LinkedHashMap：在需要输出的顺序和输入的顺序相同的情况下。

## 5. ConcurrentHashMap 在 JDK 1.8 中，为什么要使用内置锁 synchronized 来代替重入锁 ReentrantLock？

①、粒度降低了；

②、JVM 开发团队改进了 synchronized，而且基于 JVM 的 synchronized 优化空间更大，更加自然。

③、在大量的数据操作下，对于 JVM 的内存压力，基于 API 的 ReentrantLock 会开销更多的内存。

## 6. ConcurrentHashMap 简单介绍？

①、重要的常量：

private transient volatile int sizeCtl;

当为负数时，-1 表示正在初始化，-N 表示 N - 1 个线程正在进行扩容；

当为 0 时，表示 table 还没有初始化；

当为其他正数时，表示初始化或者下一次进行扩容的大小。

②、数据结构：

Node 是存储结构的基本单元，继承 HashMap 中的 Entry，用于存储数据；

TreeNode 继承 Node，但是数据结构换成了二叉树结构，是红黑树的存储结构，用于红黑树中存储数据；

TreeBin 是封装 TreeNode 的容器，提供转换红黑树的一些条件和锁的控制。

③、存储对象时（put() 方法）：

如果没有初始化，就调用 initTable() 方法来进行初始化；

如果没有 hash 冲突就直接 CAS 无锁插入；

如果需要扩容，就先进行扩容；

如果存在 hash 冲突，就加锁来保证线程安全，两种情况：一种是链表形式就直接遍历到尾端插入，一种是红黑树就按照红黑树结构插入；

如果该链表的数量大于阀值 8，就要先转换成红黑树的结构，break 再一次进入循环

如果添加成功就调用 addCount() 方法统计 size，并且检查是否需要扩容。

④、扩容方法 transfer()：默认容量为 16，扩容时，容量变为原来的两倍。

helpTransfer()：调用多个工作线程一起帮助进行扩容，这样的效率就会更高。

⑤、获取对象时（get()方法）：

计算 hash 值，定位到该 table 索引位置，如果是首结点符合就返回；

如果遇到扩容时，会调用标记正在扩容结点 ForwardingNode.find()方法，查找该结点，匹配就返回；

以上都不符合的话，就往下遍历结点，匹配就返回，否则最后就返回 null。

## 7. jdk 1.7  ConcurrentHashMap的实现

ConcurrentHashMap是由Segment数组结构和HashEntry数组结构组成。Segment实际继承自可重入锁（ReentrantLock），在ConcurrentHashMap里扮演锁的角色；HashEntry则用于存储键值对数据。一个ConcurrentHashMap里包含一个Segment数组，每个Segment里包含一个HashEntry数组，我们称之为table，每个HashEntry是一个链表结构的元素。

**常见面试题：ConcurrentHashMap实现原理是怎么样的或者问ConcurrentHashMap如何在保证高并发下线程安全的同时实现了性能提升？**

答：ConcurrentHashMap允许多个修改操作并发进行，其关键在于使用了**锁分段**技术。**它使用了多个锁来控制对hash表的不同部分进行的修改。内部使用段(Segment)来表示这些不同的部分，每个段其实就是一个小的hash table，只要多个修改操作发生在不同的段上，它们就可以并发进行。**

**有些方法需要跨段，比如size()和containsValue()，它们可能需要锁定整个表而而不仅仅是某个段，这需要按顺序锁定所有段，操作完毕后，又按顺序释放所有段的锁。**这里“按顺序”是很重要的，否则极有可能出现死锁，在ConcurrentHashMap内部，段数组是final的，并且其成员变量实际上也是final的，但是，仅仅是将数组声明为final的并不保证数组成员也是final的，这需要实现上的保证。这可以确保不会出现死锁，因为获得锁的顺序是固定的。

ConcurrentHashMap 类中包含两个静态内部类 HashEntry 和 Segment。

**HashEntry 用来封装映射表的键 / 值对；Segment 用来充当锁的角色，每个 Segment 对象守护整个散列映射表的若干个桶。**

每个桶是由若干个 HashEntry 对象链接起来的链表。一个 ConcurrentHashMap 实例中包含由若干个 Segment 对象组成的数组。

每个Segment守护者一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改时，必须首先获得它对应的Segment锁。

### **HashEntry类**

```java
static final class HashEntry<K,V> { 
            final K key;                 // 声明 key 为 final 型
            final int hash;              // 声明 hash 值为 final 型 
            volatile V value;           // 声明 value 为 volatile 型
            final HashEntry<K,V> next;  // 声明 next 为 final 型 
 
 
            HashEntry(K key, int hash, HashEntry<K,V> next, V value)  { 
                this.key = key; 
                this.hash = hash; 
                this.next = next; 
                this.value = value; 
            } 
     }
```

每个HashEntry代表Hash表中的一个节点，在其定义的结构中可以看到，除了value值没有定义final，其余的都定义为final类型，我们知道Java中关键词final修饰的域成为最终域。

用关键词final修饰的变量一旦赋值，就不能改变，也称为修饰的标识为常量。这就意味着我们删除或者增加一个节点的时候，就必须从头开始重新建立Hash链，因为next引用值需要改变。 

由于 HashEntry 的 next 域为 final 型，**所以新节点只能在链表的表头处插入**。 

### **segment类**

```java
static final class Segment<K,V> extends ReentrantLock implements Serializable {  
     private static final long serialVersionUID = 2249069246763182397L;  
             /** 
              * 在本 segment 范围内，包含的 HashEntry 元素的个数
              * 该变量被声明为 volatile 型,保证每次读取到最新的数据
              */  
             transient volatile int count;  
 
             /** 
              *table 被更新的次数
              */  
             transient int modCount;  
 
             /** 
              * 当 table 中包含的 HashEntry 元素的个数超过本变量值时，触发 table 的再散列
              */  
             transient int threshold;  
 
             /** 
              * table 是由 HashEntry 对象组成的数组
              * 如果散列时发生碰撞，碰撞的 HashEntry 对象就以链表的形式链接成一个链表
              * table 数组的数组成员代表散列映射表的一个桶
              * 每个 table 守护整个 ConcurrentHashMap 包含桶总数的一部分
              * 如果并发级别为 16，table 则守护 ConcurrentHashMap 包含的桶总数的 1/16 
              */  
             transient volatile HashEntry<K,V>[] table;  
 
 
             /** 
              * 装载因子
              */  
             final float loadFactor;  
     }
```

**Segment 类继承于 ReentrantLock 类，从而使得 Segment 对象能充当锁的角色。每个 Segment 对象用来守护其（成员对象 table 中）包含的若干个桶。**

table 是一个由 HashEntry 对象组成的数组。table 数组的每一个数组成员就是散列映射表的一个桶。 

每一个 Segment 对象都有一个 count 对象来表示本 Segment 中包含的 HashEntry 对象的总数。 

之所以在每个 Segment 对象中包含一个计数器，而不是在 ConcurrentHashMap 中使用全局的计数器，是为了避免出现“热点域”而影响ConcurrentHashMap 的并发性。

### **ConcurrentHashMap 类** 

默认的情况下，每个ConcurrentHashMap 类会创建16个并发的segment，每个segment里面包含多个Hash表，每个Hash链都是有HashEntry节点组成的。 



如果键能均匀散列，每个 Segment 大约守护整个散列表中桶总数的 1/16。 

```java
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> 
           implements ConcurrentMap<K, V>, Serializable { 
     
           /** 
        * 散列映射表的默认初始容量为 16，即初始默认为 16 个桶
        * 在构造函数中没有指定这个参数时，使用本参数
        */ 
       static final     int DEFAULT_INITIAL_CAPACITY= 16; 
     
       /** 
        * 散列映射表的默认装载因子为 0.75，该值是 table 中包含的 HashEntry 元素的个数与
    * table 数组长度的比值
        * 当 table 中包含的 HashEntry 元素的个数超过了 table 数组的长度与装载因子的乘积时，
    * 将触发 再散列
        * 在构造函数中没有指定这个参数时，使用本参数
        */ 
       static final float DEFAULT_LOAD_FACTOR= 0.75f; 
     
       /** 
        * 散列表的默认并发级别为 16。该值表示当前更新线程的估计数
        * 在构造函数中没有指定这个参数时，使用本参数
        */ 
       static final int DEFAULT_CONCURRENCY_LEVEL= 16; 
     
       /** 
        * segments 的掩码值
        * key 的散列码的高位用来选择具体的 segment 
        */ 
       final int segmentMask; 
     
       /** 
        * 偏移量
        */ 
       final int segmentShift; 
     
       /** 
        * 由 Segment 对象组成的数组
        */ 
       final Segment<K,V>[] segments; 
     
       /** 
        * 创建一个带有指定初始容量、加载因子和并发级别的新的空映射。
        */ 
       public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) { 
           if(!(loadFactor > 0) || initialCapacity < 0 || 
    concurrencyLevel <= 0) 
               throw new IllegalArgumentException(); 
     
           if(concurrencyLevel > MAX_SEGMENTS) 
               concurrencyLevel = MAX_SEGMENTS; 
     
           // 寻找最佳匹配参数（不小于给定参数的最接近的 2 次幂） 
           int sshift = 0; 
           int ssize = 1; 
           while(ssize < concurrencyLevel) { 
               ++sshift; 
               ssize <<= 1; 
           } 
           segmentShift = 32 - sshift;       // 偏移量值
           segmentMask = ssize - 1;           // 掩码值 
           this.segments = Segment.newArray(ssize);   // 创建数组
     
           if (initialCapacity > MAXIMUM_CAPACITY) 
               initialCapacity = MAXIMUM_CAPACITY; 
           int c = initialCapacity / ssize; 
           if(c * ssize < initialCapacity) 
               ++c; 
           int cap = 1; 
           while(cap < c) 
               cap <<= 1; 
     
           // 依次遍历每个数组元素
           for(int i = 0; i < this.segments.length; ++i) 
               // 初始化每个数组元素引用的 Segment 对象
    this.segments[i] = new Segment<K,V>(cap, loadFactor); 
       } 
     
       /** 
        * 创建一个带有默认初始容量 (16)、默认加载因子 (0.75) 和 默认并发级别 (16) 
     * 的空散列映射表。
        */ 
       public ConcurrentHashMap() { 
           // 使用三个默认参数，调用上面重载的构造函数来创建空散列映射表
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL); 
    }
```

**用分离锁实现多个线程间的并发写操作**

  插入数据后的ConcurrentHashMap的存储形式

### Put操作源码

 首先，根据 key 计算出对应的 hash 值：

```java
	public V put(K key, V value) { 
       if (value == null)          //ConcurrentHashMap 中不允许用 null 作为映射值
           throw new NullPointerException(); 
       int hash = hash(key.hashCode());        // 计算键对应的散列码
       // 根据散列码找到对应的 Segment 
       return segmentFor(hash).put(key, hash, value, false); 
    }
     根据 hash 值找到对应的 Segment：
    /** 
    * 使用 key 的散列码来得到 segments 数组中对应的 Segment 
    */ 
    final Segment<K,V> segmentFor(int hash) { 
    // 将散列值右移 segmentShift 个位，并在高位填充 0 
    // 然后把得到的值与 segmentMask 相“与”
    // 从而得到 hash 值对应的 segments 数组的下标值
    // 最后根据下标值返回散列码对应的 Segment 对象
        return segments[(hash >>> segmentShift) & segmentMask]; 
    }
```

在这个 Segment 中执行具体的 put 操作：

```java
V put(K key, int hash, V value, boolean onlyIfAbsent) { 
           lock();  // 加锁，这里是锁定某个 Segment 对象而非整个 ConcurrentHashMap 
           try { 
               int c = count; 
 
               if (c++ > threshold)     // 如果超过再散列的阈值
                   rehash();              // 执行再散列，table 数组的长度将扩充一倍
 
               HashEntry<K,V>[] tab = table; 
               // 把散列码值与 table 数组的长度减 1 的值相“与”
               // 得到该散列码对应的 table 数组的下标值
               int index = hash & (tab.length - 1); 
               // 找到散列码对应的具体的那个桶
               HashEntry<K,V> first = tab[index]; 
 
               HashEntry<K,V> e = first; 
               while (e != null && (e.hash != hash || !key.equals(e.key))) 
                   e = e.next; 
 
               V oldValue; 
               if (e != null) {            // 如果键 / 值对以经存在
                   oldValue = e.value; 
                   if (!onlyIfAbsent) 
                       e.value = value;    // 设置 value 值
               } 
               else {                        // 键 / 值对不存在 
                   oldValue = null; 
                   ++modCount;         // 要添加新节点到链表中，所以 modCont 要加 1  
                   // 创建新节点，并添加到链表的头部 
                   tab[index] = new HashEntry<K,V>(key, hash, first, value); 
                   count = c;               // 写 count 变量
               } 
               return oldValue; 
           } finally { 
               unlock();                     // 解锁
           } 
       }
```



这里的加锁操作是针对（键的 hash 值对应的）某个具体的 Segment，锁定的是该 Segment 而不是整个 ConcurrentHashMap。

因为插入键 / 值对操作只是在这个 Segment 包含的某个桶中完成，不需要锁定整个ConcurrentHashMap。

此时，其他写线程对另外 15 个Segment 的加锁并不会因为当前线程对这个 Segment 的加锁而阻塞。

同时，所有读线程几乎不会因本线程的加锁而阻塞（除非读线程刚好读到这个 Segment 中某个 HashEntry 的 value 域的值为 null，此时需要加锁后重新读取该值）。

### Get操作实现

```java
	V get(Object key, int hash) { 
        if(count != 0) {       // 首先读 count 变量
            HashEntry<K,V> e = getFirst(hash); 
            while(e != null) { 
                if(e.hash == hash && key.equals(e.key)) { 
                    V v = e.value; 
                    if(v != null)            
                        return v; 
                    // 如果读到 value 域为 null，说明发生了重排序，加锁后重新读取
                    return readValueUnderLock(e); 
                } 
                e = e.next; 
            } 
        } 
        return null; 
    }

    V readValueUnderLock(HashEntry<K,V> e) {  
         lock();  
         try {  
             return e.value;  
         } finally {  
             unlock(); 
         }  
     }
```

ConcurrentHashMap中的读方法不需要加锁，所有的修改操作在进行结构修改时都会在最后一步写count 变量，通过这种机制保证get操作能够得到几乎最新的结构更新。

### Remove源码实现

```java
V remove(Object key, int hash, Object value) { 
        lock(); //加锁
        try{ 
            int c = count - 1; 
            HashEntry<K,V>[] tab = table; 
            //根据散列码找到 table 的下标值
            int index = hash & (tab.length - 1); 
            //找到散列码对应的那个桶
            HashEntry<K,V> first = tab[index]; 
            HashEntry<K,V> e = first; 
            while(e != null&& (e.hash != hash || !key.equals(e.key))) 
                e = e.next; 
 
 
            V oldValue = null; 
            if(e != null) { 
                V v = e.value; 
                if(value == null|| value.equals(v)) { //找到要删除的节点
                    oldValue = v; 
                    ++modCount; 
                    //所有处于待删除节点之后的节点原样保留在链表中
                    //所有处于待删除节点之前的节点被克隆到新链表中
                    HashEntry<K,V> newFirst = e.next;// 待删节点的后继结点
                    for(HashEntry<K,V> p = first; p != e; p = p.next) 
                        newFirst = new HashEntry<K,V>(p.key, p.hash, 
                                                      newFirst, p.value); 
                    //把桶链接到新的头结点
                    //新的头结点是原链表中，删除节点之前的那个节点
                    tab[index] = newFirst; 
                    count = c;      //写 count 变量
                } 
            } 
            return oldValue; 
        } finally{ 
            unlock(); //解锁
        } 
    }
```

整个操作是在持有段锁的情况下执行的，空白行之前的行主要是定位到要删除的节点e。

如果不存在这个节点就直接返回null，否则就要将e前面的结点复制一遍，尾结点指向e的下一个结点。

e后面的结点不需要复制，它们可以重用。

中间那个for循环是做什么用的呢？从代码来看，就是将定位之后的所有entry克隆并拼回前面去，但有必要吗？

每次删除一个元素就要将那之前的元素克隆一遍？这点其实是由entry的不变性来决定的，仔细观察entry定义，发现除了value，其他所有属性都是用final来修饰的，

**这意味着在第一次设置了next域之后便不能再改变它，取而代之的是将它之前的节点全都克隆一次。至于entry为什么要设置为不变性，这跟不变性的访问不需要同步从而节省时间有关。**

![img](https://img2018.cnblogs.com/blog/1415794/201905/1415794-20190526235834579-1687161395.png)

 

 

### containsKey操作实现，它不需要读取值。

```java
	boolean containsKey(Object key, int hash) {  
         if (count != 0) { // read-volatile  
             HashEntry<K,V> e = getFirst(hash);  
             while (e != null) {  
                 if (e.hash == hash && key.equals(e.key))  
                     return true;  
                 e = e.next;  
             }  
         }  
         return false;  
     } 
```

size**操作实现**

我们要统计整个ConcurrentHashMap里元素的大小，就必须统计所有Segment里元素的大小后求和。

Segment里的全局变量count是一个volatile变量，那么在多线程场景下，我们是不是直接把所有Segment的count相加就可以得到整个ConcurrentHashMap大小了呢？不是的，虽然相加时可以获取每个Segment的count的最新值，但是拿到之后可能累加前使用的count发生了变化，那么统计结果就不准了。

所以最安全的做法，是在统计size的时候把所有Segment的put，remove和clean方法全部锁住，但是这种做法显然非常低效。

**因为在累加count操作过程中，之前累加过的count发生变化的几率非常小，所以ConcurrentHashMap的做法是先尝试2次通过不锁住Segment的方式来统计各个Segment大小，如果统计的过程中，容器的count发生了变化，则再采用加锁的方式来统计所有Segment的大小。**

那么ConcurrentHashMap是如何判断在统计的时候容器是否发生了变化呢？**使用modCount变量，在put , remove和clean方法里操作元素前都会将变量modCount进行加1，那么在统计size前后比较modCount是否发生变化，从而得知容器的大小是否发生变化。**

**总结**

ConcurrentHashMap 的高并发性主要来自于三个方面：

用分离锁实现多个线程间的更深层次的共享访问。

用 HashEntery 对象的不变性来降低执行读操作的线程在遍历链表期间对加锁的需求。

通过对同一个 Volatile 变量的写 / 读访问，协调不同线程间读 / 写操作的内存可见性。

使用分离锁，减小了请求同一个锁的频率。