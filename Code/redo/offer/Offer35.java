package com.todd.redo.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author todd
 * @date 2020/8/25 8:45
 * @description: 剑指 Offer 35. 复杂链表的复制
 */
public class Offer35 {
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
            map.get(p).next=map.get(p.next);
            map.get(p).random=map.get(p.random);
            p=p.next;
        }
        return map.get(head);
    }
}
