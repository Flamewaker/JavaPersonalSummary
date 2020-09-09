package com.todd.code;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author todd
 * @date 2020/6/16 20:40
 * @description: 翻转一棵二叉树。
 */
public class LeetCode226 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.right);
        root.right = invertTree(root.left);
        root.left = left;
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left == null && node.right == null) {
                continue;
            }
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
        }
        return root;
    }
}
