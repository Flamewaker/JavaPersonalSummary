package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/15 22:19
 * @description: 给定一个非空二叉树，返回其最大路径和。
 * 本题中，路径被定义为一条从树中任意节点出发，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
 * 关键就是区分：
 * 1. 当前节点最大路径和计算：以当前节点为起点的所有路径和 (以当前根节点作为中间节点，或作为起始节点(因为可以不往下走))
 * 2. 当前节点对上一层的贡献：只能选择当前节点的最大的一条路径作为贡献，因为路径节点不可重复
 *
 * 递归计算左右子节点的最大贡献值
 * 只有在最大贡献值大于 0 时，才会选取对应子节点
 * 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
 */
public class LeetCode124 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    int res = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return res;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMax = Math.max(0, dfs(root.left));
        int rightMax = Math.max(0, dfs(root.right));
        res = Math.max(res, root.val + leftMax + rightMax);
        return root.val + Math.max(leftMax, rightMax);
    }

}
