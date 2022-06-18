package com.todd.leetcode.offer2;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 028. 展平多级双向链表
 * 整体思路：
 * 1. 深搜 + 递归展平
 * 2. 回溯法:遇到有子节点的就暂存到栈里,等遍历完子节点在从栈中取出回溯
 * @date 4:24 PM 2022/6/16
 */
public class Offer028 {
    public Node flatten(Node head) {
        flattenList(head);
        return head;
    }

    private Node flattenList(Node curr) {
        Node last = curr;
        while(curr != null){
            Node next = curr.next;
            // 递归地处理child
            if(curr.child != null) {
                // 先递归地处理child，得到当前链的最后一个结点
                Node childLast = flattenList(curr.child);
                curr.next = curr.child;
                curr.child.prev = curr;
                curr.child = null;
                if(next != null){
                    childLast.next = next;
                    next.prev = childLast;
                }
                last = childLast;
            } else {
                last = curr;
            }
            curr = curr.next;
        }
        return last;
    }

    public Node flattenOptimized(Node head) {
        Node node = head;
        Node prev = null;
        Deque<Node> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node == null) {
                node = stack.pop();
                node.prev = prev;
                prev.next = node;
            }
            if (node.child != null) {
                if (node.next != null) {
                    stack.push(node.next);
                }
                node.child.prev = node;
                node.next = node.child;
                node.child = null;
            }
            prev = node;
            node = node.next;
        }
        return head;
    }

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

}
