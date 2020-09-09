package com.todd.redo.offer;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/25 8:47
 * @description: 剑指 Offer 36. 二叉搜索树与双向链表
 */
public class Offer36 {

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        LinkedList<Node> stack = new LinkedList<>();
        Node cur = root;
        Node pre = null, head = null, tail = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            tail = cur;
            if (pre == null) {
                head = cur;
            } else {
                pre.right = cur;
                cur.left = pre;
            }
            pre = cur;
            cur = cur.right;
        }
        head.left = tail;
        tail.right = head;
        return head;
    }
}
