package com.todd.code;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
 * 递归实现 + 队列实现bfs或栈实现dfs
 * 注意: root == null 是判断是否继续向下搜索的条件
 * root.left == null && root.right == null 直接跳出搜索过程
 *
 * @Author todd
 * @Date 2020/4/17
 */
public class Offer27 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 递归实现
     *
     * @param root
     * @return TreeNode
     */
    public TreeNode mirrorTree(TreeNode root) {
        mirrorRecursively(root);
        return root;
    }

    public void mirrorRecursively(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        TreeNode newNode = root.left;
        root.left = root.right;
        root.right = newNode;
        mirrorTree(root.left);
        mirrorTree(root.right);
    }

    /**
     * 栈实现
     *
     * @param root
     * @return TreeNode
     */
    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode q = queue.poll();
            if (q.left == null && q.right == null) {
                continue;
            }
            TreeNode newNode = q.left;
            q.left = q.right;
            q.right = newNode;
            if (q.left != null) {
                queue.add(q.left);
            }
            if (q.right != null) {
                queue.add(q.right);
            }
        }
        return root;
    }
}
