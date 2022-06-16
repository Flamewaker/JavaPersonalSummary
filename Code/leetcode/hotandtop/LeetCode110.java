package com.todd.leetcode.hotandtop;

import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/4 12:06
 * @description: 平衡二叉树
 */
public class LeetCode110 {
    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return getHeight(root) != -1 ? true : false;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        if (leftHeight == -1 || rightHeight == -1 || leftHeight > rightHeight + 1 || rightHeight > leftHeight + 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean isBalanced2(TreeNode root) {
        if (null == root) {
            return true;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<int[]> heightStack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                heightStack.push(new int[]{cur.left == null ? 0 : 1, cur.right == null ? 0 : 1});
                cur = cur.left;
            }
            cur = stack.peek();
            if (cur.right == null || cur.right == pre) {
                stack.poll();

                int[] childHeight = heightStack.poll();
                if (Math.abs(childHeight[0] - childHeight[1]) > 1) {
                    return false;
                }

                if (!stack.isEmpty()) {
                    TreeNode parentNode = stack.peek();
                    int[] parentHeight = heightStack.peek();
                    int h = Math.max(childHeight[0], childHeight[1]);
                    if (parentNode.left == cur) {
                        parentHeight[0] += h;
                    } else {
                        parentHeight[1] += h;
                    }
                }

                pre = cur;
                cur = null;
            } else {
                cur = cur.right;
            }
        }
        return true;
    }
}
