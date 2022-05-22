package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 101. 对称二叉树
 * 整体思路：
 * 左子树和右子相等，也就是说要递归的比较左子树和右子树
 * @date 3:31 PM 2022/5/22
 */
public class LeetCode101 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return judgeSymmetric(root.left, root.right);
    }

    private boolean judgeSymmetric(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return judgeSymmetric(root1.left, root2.right) && judgeSymmetric(root1.right, root2.left);
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
