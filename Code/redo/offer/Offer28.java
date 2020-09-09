package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 23:27
 * @description: 剑指 Offer 28. 对称的二叉树
 */
public class Offer28 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return judgeSymmetric(root.left, root.right);
    }

    private boolean judgeSymmetric(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null) {
            return true;
        }
        if (leftNode == null || rightNode == null) {
            return false;
        }
        if (leftNode.val != rightNode.val) {
            return false;
        }
        return judgeSymmetric(leftNode.left, rightNode.right) && judgeSymmetric(leftNode.right, rightNode.left);
    }
}
