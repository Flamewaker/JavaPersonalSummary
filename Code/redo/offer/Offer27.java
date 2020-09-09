package com.todd.redo.offer;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/24 23:10
 * @description: 剑指 Offer 27. 二叉树的镜像
 */
public class Offer27 {
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        LinkedList<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()) {
            TreeNode cur = que.pollFirst();
            if (cur.left == null && cur.right == null) {
                continue;
            }
            TreeNode temp = cur.left;
            cur.left = cur.right;
            cur.right = temp;

            if (cur.left != null) {
                que.offerLast(cur.left);
            }
            if (cur.right != null) {
                que.offerLast(cur.right);
            }
        }
        return root;
    }
}
