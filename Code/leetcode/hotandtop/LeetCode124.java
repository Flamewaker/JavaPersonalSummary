package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 124. 二叉树中的最大路径和
 * 整体思路：
 * 二叉树的最大路径对于每个节点向下搜索
 * 1. 当前节点的值 + 左边的最大值 + 右边的最大值
 * @date 10:55 PM 2022/6/4
 */
public class LeetCode124 {
    int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        searchMaxPath(root);
        return ans;
    }

    public int searchMaxPath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftVal = Math.max(0, searchMaxPath(root.left));
        int rightVal = Math.max(0, searchMaxPath(root.right));
        ans = Math.max(ans, root.val + leftVal + rightVal);
        return leftVal > rightVal ? leftVal + root.val : rightVal + root.val;
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
