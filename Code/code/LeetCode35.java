package com.todd.code;

import java.util.HashMap;
import java.util.Map;

/**
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 * 使用辅助空间，将信息放在hash表中。首先创建相关节点，其次将相关指针进行指向。
 * 需要保留链表信息的问题可以考虑hash表保留指针信息。
 *
 * @Author todd
 * @Date 2020/4/22
 */
public class LeetCode35 {

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public Node copyRandomList(Node head) {
        Map<Node,Node> map=new HashMap<>();
        Node p=head;
        while(p!=null){
            map.put(p,new Node(p.val));
            p=p.next;
        }
        p=head;
        while(p!=null){
            //新节点的next 就是 旧节点next对应的新节点
            map.get(p).next=map.get(p.next);
            //新节点的random 就是 旧节点random对应的新节点
            map.get(p).random=map.get(p.random);
            p=p.next;
        }
        return map.get(head);
    }
}
