package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/24 15:34
 * @description: 111. 二叉树的最小深度
 */
public class LeetCode111 {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            min = Math.min(min, minDepth(root.left));
        }
        if (root.right != null) {
            min = Math.min(min, minDepth(root.right));
        }
        return min + 1;
    }

}
