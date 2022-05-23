package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 226. 翻转二叉树
 * 整体思路：
 * 1. 递归翻转整个二叉树即可
 * @date 12:56 PM 2022/5/23
 */
public class LeetCode226 {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
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
