package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 029. 排序的循环链表
 * 1、找最大值和最小值所在的点，注意，这里的极值点不一定是队伍的头和尾，因为可能有重复值；
 * 2、若极大值==极小值，说明所有节点值都相同，直接插在头结点后面；
 * 3、若插入值>=极大值 || 插入值<=极小值，则通过去重，找到头尾节点，将节点插入头或尾；
 * 4、若插入值在（极小值，极大值）范围内，则找到相应位置插入即可。
 * @date 12:19 AM 2022/6/17
 */
public class Offer029 {
    public Node insert(Node head, int insertVal) {
        if (head == null) {
            Node node = new Node(insertVal);
            node.next = node;
            return node;
        }
        Node max = head, min = head;
        Node p = head.next;
        while (p != head) {
            if (p.val > max.val) {
                max = p;
            }
            if (p.val < min.val) {
                min = p;
            }
            p = p.next;
        }
        if (max == min) {
            max.next = new Node(insertVal, max.next);
        } else if (insertVal >= max.val || insertVal <= min.val) {
            while (max.next.val == max.val) {
                max = max.next;
            }
            max.next = new Node(insertVal, max.next);
        } else {
            while (min.next.val < insertVal) {
                min = min.next;
            }
            min.next = new Node(insertVal, min.next);
        }
        return head;
    }

    class Node {
        public int val;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    ;
}
