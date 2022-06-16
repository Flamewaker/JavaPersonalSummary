package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 145. 二叉树的后序遍历
 * @date 12:09 AM 2022/6/5
 */
public class LeetCode145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;
        TreeNode pre = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.offerLast(curr);
                curr = curr.left;
            }
            curr = stack.peekLast();
            if (curr.right == null || curr.right == pre) {
                ans.add(curr.val);
                stack.pollLast();
                pre = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }
        return ans;
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
