package com.todd.leetcode.offer2;

import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 053. 二叉搜索树中的中序后继
 * @date 4:56 PM 2022/7/11
 */
public class Offer053 {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        TreeNode curr = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        boolean flag = false;
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.offerLast(curr);
                curr = curr.left;
            }
            curr = stack.pollLast();
            if (flag == true) {
                return curr;
            }
            if (curr == p) {
                flag = true;
            }
            curr = curr.right;
        }
        return null;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}
