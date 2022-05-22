package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 104. 二叉树的最大深度
 * 整体思路：
 * 简单递归， max(leftDepth, rightDepth) + 1
 * @date 4:03 PM 2022/5/22
 */
public class LeetCode104 {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }

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

}
