package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/9 19:13
 * @description: TODO
 */
public class NC6 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }

    public int ans = Integer.MIN_VALUE;

    public int maxPathSum (TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxSum(root);
        return ans == Integer.MIN_VALUE ? 0 : ans;
    }

    public int maxSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftValue = Math.max(0, maxSum(root.left));
        int rightValue = Math.max(0, maxSum(root.right));
        ans = Math.max(ans, Math.max(leftValue + rightValue + root.val, Math.max(leftValue, rightValue) + root.val));
        return Math.max(leftValue, rightValue) + root.val;

    }
}
