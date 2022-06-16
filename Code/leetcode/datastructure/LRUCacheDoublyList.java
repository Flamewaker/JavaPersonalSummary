package com.todd.leetcode.datastructure;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/5/19 8:56
 * @description: LeetCode 146 面试题 16.25. LRU缓存 重点题目
 * 设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。
 * 缓存应该从键映射到值(允许你插入和检索特定键对应的值)，并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。
 *
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
 */
public class LRUCacheDoublyList {

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    // HashMap: <key = Question, value = ListNode>
    // LinkedList: <Answer>

    class Node {
        int key;
        int value;
        Node pre;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> hashMap;
    private Node head;
    private Node tail;
    private int cap;

    public LRUCacheDoublyList(int capacity) {
        hashMap = new HashMap<>(capacity);
        cap = capacity;
    }

    public int get(int key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return -1;
        } else {
            int val = node.value;
            remove(node);
            appendHead(node);
            return val;
        }
    }

    public void put(int key, int value) {
        Node node = hashMap.get(key);
        if (node == null) {
            node = new Node(key, value);
            if (hashMap.size() >= cap) {
                //为什么要在Node中记录key的原因是：删除HashMap中的reference
                hashMap.remove(tail.key);
                remove(tail);
            }
            appendHead(node);
            hashMap.put(key, node);
        } else {
            node.value = value;
            remove(node);
            appendHead(node);
        }
    }

    private void remove(Node curr) {
        if(head == tail) {
            head = tail = null;
        } else if (curr == head) {
            head = curr.next;
            head.pre = null;
            curr.next = null;
        } else if (curr == tail) {
            tail = curr.pre;
            tail.next = null;
            curr.pre = null;
        } else {
            curr.pre.next = curr.next;
            curr.next.pre = curr.pre;
            curr.next = null;
            curr.pre = null;
        }
    }

    private void appendHead(Node curr) {
        if (head == null && tail == null) {
            head = curr;
            tail = curr;
        } else {
            head.pre = curr;
            curr.next = head;
            head = curr;
        }
    }



    /**
     * 技术面试解析（以LRUCache问题为例）
     * 最好能按照如下的思路解释给面试官听。
     * 1. 阐述问题
     * 2. 分析思路、时空复杂度、分析哪里可以优化、如何优化
     * 3. 写代码
     * 4. 跑测试用例
     *
     * 具体对应到下面4步：
     * 1. 面试官通常都不会一次性给你所有条件，而是需要你思考之后去问他。那通过这个环节，面试官就知道了你遇到问题是怎么去思考的，
     * 你考虑的是否全面，怎么去和别人沟通的，今后和你一起工作的状态是怎样的。如果代码都写完了面试官说这不是我想问的，那时候已经没时间了，买单的是我们自己。
     * 2. 分析思路、时空复杂度、分析哪里可以优化、如何优化（后面分析）
     * 3. 代码功底要牢固
     * 4. 过几个例子检验一下，是很好的。一来是给自己一个修正的机会，因为有很多 bug 是你跑两个例子就能发现的。二来是展示你的这种意识，跑测试的意识。
     *
     * 这道题目考查的东西有很多，可难可易，一来考察数据结构，写代码的能力；而来考察拓展及延伸，聊一下 Redis 中的近似 LRU 算法，所以 follow up 就可以无限的深入下去。
     *
     * 1. LRU
     * LRU = Least Recently Used 最近最少使用
     * 它是一种缓存逐出策略。（目前最常使用）
     * LRU 算法是假设最近最少使用的那些信息，将来被使用的概率也不大，所以在容量有限的情况下，就可以把这些不常用的信息踢出去，腾地方。
     * FIFO (First In First Out) 这个就是普通的先进先出。
     * LFU (Least Frequently Used) 这个是计算每个信息的访问次数，踢走访问次数最少的那个；如果访问次数一样，就踢走好久没用过的那个。这个算法其实很高效，但是耗资源，所以一般不用。
     * LRU (Least Recently Used) 这是目前最常用了。
     *
     * LRU 的规则是把很长时间没有用过的踢出去，那它的隐含假设就是，认为最近用到的信息以后用到的概率会更大。
     *
     * 2. Cache 是什么？
     * 把一些可以重复使用的信息存起来，以便之后需要时可以快速拿到。比如，在进行数据库查询的时候，不想每次请求都去 call 数据库，那我们就在内存里存一些常用的数据，来提高访问性能。
     * 这种设计思想其实是遵循了著名的“二八定律”。在读写数据库时，每次的 I/O 过程消耗很大，但其实 80% 的 request 都是在用那 20% 的数据，所以把这 20% 的数据放在内存里，就能够极大的提高整体的效率。
     * 总之，Cache 的目的是存一些可以复用的信息，方便将来的请求快速获得。
     *
     * 这道经典题大家都知道是要用 HashMap + Doubly Linked List，或者说用 Java 中现成的 LinkedHashMap，但是，为什么？你是怎么想到用这两个数据结构的？面试的时候不讲清楚这个，不说清楚思考过程，代码写对了也没用。
     * 和在工作中的设计思路类似，没有人会告诉我们要用什么数据结构，一般的思路是先想有哪些 operations，然后根据这些操作，再去看哪些数据结构合适。
     *
     * 3. 分析 Operations
     * 1. 首先最基本的操作就是能够从里面读信息；
     * 2. 那还得能加入新的信息，新的信息进来就是 most recently used 了；
     * 3. 在加新信息之前，还得看看有没有空位，如果没有空间了，得先把老的踢出去，那就需要能够找到那个老家伙并且删除它；
     * 4. 那如果加入的新信息是缓存里已经有的，那意思就是 key 已经有了，要更新 value，那就只需要调整一下这条信息的 priority，它已经从上一次被使用升级为最新使用的了。
     *
     * 4. 找寻数据结构
     * 第一个操作很明显，我们需要一个能够快速查找的数据结构，非 HashMap 莫属，可是后面的操作 HashMap 就不行了。
     * 我们来数一遍基本的数据结构：Array, LinkedList, Stack, Queue, Tree, BST, Heap, HashMap
     * Array, Stack, Queue 这三种本质上都是 Array 实现的（当然 Stack, Queue 也可以用 LinkedList 来实现）。用数组实现时间复杂度为O（n）。
     * BST 同理，时间复杂度是 O(logn)。Heap 即便可以，也是 O(logn).
     * LinkedList，按照从老到新的顺序，删除、插入、移动，都可以是 O(1) 的！但是删除时还需要一个 previous pointer 才能删掉，所以我需要一个 Doubly List.
     * 数据结构： HashMap + DoublyList（双向链表）
     *
     * 5. 定义清楚数据结构的内容
     * 选好了数据结构之后，还需要定义清楚每个数据结构具体存储的是什么，这两个数据结构是如何联系的，这才是核心问题。
     * 1. 读信息，直接利用HashMap读取Answer，时间复杂度O（1）。
     * 2. 加入一组新的数据，如果没有这个Key，加进来，添加到链表的首部；如果已经有这个Key，HashMap这里要更新一下Value，还需要吧该节点移动到链表的首部，因为最新被使用了。
     * 为了达到更新链表的操作，需要记录节点的位置。因此HashMap中不是直接存放的Value，而是存放一个记录Value的节点指针。得到了节点自然也就能得到value。
     * 之后我们更新、移动每个节点时，它的 reference 也不需要变，所以 HashMap也不用改动，动的只是当前节点的指针指向pre， next.
     * 最后的数据机构如下：
     * HashMap                Doubly List
     * Key   =>  Node   =>    Value, pre, next
     *
     * Java 中的 LinkedHashMap 已经做了很好的实现。
     *
     * 6. 总结
     * 1. 第一个操作，get() API，直接读取并更新节点在链表中位置即可；
     * 2，第二个操作，put() API，画图的时候边讲边写，每一步都从 high level 到 detail 再到代码，把代码模块化。
     * put() => 有 Key => 更新Value, 更新节点在链表中的位置
     *       => 无 Key => 有空位置么 => 有   => appendHead()
     *                             => 没有 => remove() + appendHead()
     * 我们需要的是能够去解决未知的问题的能力，知识可能会被遗忘，但是思考问题的方式方法是一直跟随着我们的，也是我们需要不断提高的。
     */
}
