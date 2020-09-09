package com.todd.code;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/7/2 22:14
 * @description: 110. 平衡二叉树
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：
 */
public class LeetCode110 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    Boolean balance = true;
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        judgeBananced(root);
        return balance;
    }

    public int judgeBananced(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = judgeBananced(root.left);
        int rightHeight = judgeBananced(root.right);
        int difference = leftHeight - rightHeight;
        if (difference > 1 || difference < -1) {
            balance = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // 优化

    public boolean isBalanced2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return judgeBananced2(root) >= 0;
    }

    public int judgeBananced2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = judgeBananced2(root.left);
        int rightHeight = judgeBananced2(root.right);
        int difference = leftHeight - rightHeight;
        if (leftHeight < 0 || rightHeight < 0 || difference > 1 || difference < -1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 判断平衡二叉树非递归实现
     * @param root
     * @return
     */
    public boolean isBalanced3(TreeNode root) {
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
