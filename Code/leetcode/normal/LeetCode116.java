package com.todd.leetcode.normal;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/8 11:36
 * @description: 116. 填充每个节点的下一个右侧节点指针
 * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 */
public class LeetCode116 {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.offerLast(root);
        while (!queue.isEmpty()){
            Node last = null;
            Node cur = null;
            for (int i = queue.size(); i > 0; i--) {
                cur = queue.pollFirst();
                if (last != null) {
                    last.next = cur;
                }
                if (cur.left != null) {
                    queue.offerLast(cur.left);
                }
                if (cur.right != null) {
                    queue.offerLast(cur.right);
                }
                last = cur;
            }
            cur.next = null;
        }
        return root;
    }
}
