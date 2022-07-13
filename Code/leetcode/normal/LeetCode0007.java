package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/15 8:45
 * @description: TODO
 */
public class LeetCode0007 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildBinaryTree(preorder, inorder, 0, 0, inorder.length - 1);
    }

    public TreeNode buildBinaryTree(int[] preorder, int[] inorder, int preStart, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int mid = inStart;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == preorder[preStart]) {
                mid = i;
                break;
            }
        }
        root.left = buildBinaryTree(preorder, inorder, preStart + 1, inStart, mid - 1);
        root.right = buildBinaryTree(preorder, inorder, preStart + mid - inStart + 1, mid + 1, inEnd);
        return root;
    }
}
