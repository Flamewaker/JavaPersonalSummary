package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 051. 节点之和最大的路径
 * @date 7:09 PM 2022/6/17
 */
public class Offer051 {
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
        int left = Math.max(0, searchMaxPath(root.left));
        int right = Math.max(0, searchMaxPath(root.right));
        ans = Math.max(ans, left + right + root.val);
        return left > right ? left + root.val : right + root.val;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

