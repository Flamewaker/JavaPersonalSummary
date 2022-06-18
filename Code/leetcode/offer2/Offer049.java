package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 049. 从根节点到叶节点的路径数字之和
 * @date 6:54 PM 2022/6/17
 */
public class Offer049 {

    int ans = 0;

    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return ans;
        }
        dfs(root, 0);
        return ans;
    }

    private void dfs(TreeNode root, int currVal) {
        if (root == null) {
            return;
        }
        int nextVal = currVal * 10 + root.val;
        if (root.left == null && root.right == null) {
            ans += nextVal;
        }
        dfs(root.left,  nextVal);
        dfs(root.right, nextVal);
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
