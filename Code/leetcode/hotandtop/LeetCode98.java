package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 98. 验证二叉搜索树 (*)
 * 中序遍历时，判断当前节点是否大于中序遍历的前一个节点，如果大于，说明满足 BST，继续遍历；否则直接返回 false。
 * @date 2:00 PM 2022/5/22
 */
public class LeetCode98 {
    int pre = Integer.MAX_VALUE;
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 1. 访问左子树
        if (!isValidBST(root.left)) {
            return false;
        }
        // 2. 访问当前节点：如果当前节点小于等于中序遍历的前一个节点，说明不满足BST，返回 false；否则继续遍历。
        if (root.val <= pre) {
            return false;
        }
        // 3. 访问右子树
        pre = root.val;
        return isValidBST(root.right);
    }

    class TreeNode {
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

