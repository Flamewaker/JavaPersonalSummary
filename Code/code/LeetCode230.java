package com.todd.code;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/9 18:31
 * @description: 230. 二叉搜索树中第K小的元素
 */
public class LeetCode230 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        int count = 0;
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.right;
            }
            cur = stack.pollLast();
            count++;
            if (count == k) {
                return cur.val;
            }
            cur = cur.left;
        }
        return -1;
    }
}
