package com.todd.redo.offer;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/20 17:07
 * @description: TODO
 */
public class Offer55_2 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }

    public int recur(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = recur(root.left);
        int right = recur(root.right);
        if (right == -1 || left == -1 || Math.abs(right - left) > 1) {
            return -1;
        }
        return Math.max(left, right) + 1;
    }
}
