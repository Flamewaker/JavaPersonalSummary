package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/15 22:15
 * @description: 给定一个二叉树，检查它是否是镜像对称的。
 * 直接dfs遍历就好。
 */
public class LeetCode0101 {

    public class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return judgeSymmetric(root.left, root.right);
    }

    public boolean judgeSymmetric(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        return judgeSymmetric(a.left, b.right) && judgeSymmetric(a.right, b.left);
    }
}
