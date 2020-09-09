package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 15:07
 * @description: 剑指 Offer 55 - I. 二叉树的深度
 */
public class Offer55_1 {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }
}
