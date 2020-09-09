package com.todd.code;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，
 * 第三行再按照从左到右的顺序打印，其他行以此类推。
 * 双端队列 + BFS
 *
 * @Author todd
 * @Date 2020/4/18
 */
public class Offer32_3 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new LinkedList<>();
        Deque<TreeNode> q = new LinkedList<>();
        q.offerLast(root);
        boolean flag = false;
        while (!q.isEmpty()) {
            LinkedList<Integer> levelAns = new LinkedList<>();
            for (int i = q.size(); i > 0; i--) {
                if (flag) {
                    TreeNode cur = q.pollLast();
                    levelAns.add(cur.val);
                    if (cur.right != null) {
                        q.offerFirst(cur.right);
                    }
                    if (cur.left != null) {
                        q.offerFirst(cur.left);
                    }
                } else {
                    TreeNode cur = q.pollFirst();
                    levelAns.add(cur.val);
                    if (cur.left != null) {
                        q.offerLast(cur.left);
                    }
                    if (cur.right != null) {
                        q.offerLast(cur.right);
                    }
                }
            }

            ans.add(levelAns);
            flag = !flag;
        }

        return ans;
    }
}
