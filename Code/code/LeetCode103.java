package com.todd.code;

import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/16 21:37
 * @description: 二叉树的锯齿形层次遍历
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 */
public class LeetCode103 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<>();
        if (root == null) {
            return ans;
        }
        boolean isOddLevel = true;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            LinkedList<Integer> subAns = new LinkedList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode cur;
                if (isOddLevel) {
                    cur = queue.pollFirst();
                } else {
                    cur = queue.pollLast();
                }
                subAns.offer(cur.val);
                if (isOddLevel) {
                    if (cur.left != null) {
                        queue.offerLast(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offerLast(cur.right);
                    }
                } else {
                    if (cur.right != null) {
                        queue.offerFirst(cur.right);
                    }
                    if (cur.left != null) {
                        queue.offerFirst(cur.left);
                    }
                }
            }
            ans.add(subAns);
            isOddLevel = !isOddLevel;
        }
        return ans;
    }
}
