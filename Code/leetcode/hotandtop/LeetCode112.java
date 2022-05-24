package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 112. 路径总和
 * 整体思路：
 * 从根节点开始，每当遇到一个节点的时候，从目标值里扣除节点值，一直到叶子节点判断目标值是不是被扣完。
 * @date 12:18 PM 2022/5/24
 */
public class LeetCode112 {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        int next = targetSum - root.val;
        if (next == 0 && root.left == null && root.right == null) {
            return true;
        }
        return hasPathSum(root.left, next) || hasPathSum(root.right, next);
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
