package com.todd.code;

/**
 * @author todd
 * @date 2020/7/28 14:15
 * @description: 129. 求根到叶子节点数字之和
 * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
 */
public class LeetCode129 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public int sumNumbers(TreeNode root) {
        return helper(root, 0);
    }

    public int ans = 0;
    public void dfs(TreeNode root, int currValue) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            ans += currValue * 10 + root.val;
        }

        if (root.left != null) {
            dfs(root.left, currValue * 10 + root.val);
        }

        if (root.right != null) {
            dfs(root.right, currValue * 10 + root.val);
        }
    }

    //精简版

    public int helper(TreeNode root, int currValue) {
        if (root == null) {
            return 0;
        }
        int temp = currValue * 10 + root.val;
        if (root.left == null && root.right == null) {
            return temp;
        }
        return  helper(root.left, temp) + helper(root.right, temp);
    }
}
