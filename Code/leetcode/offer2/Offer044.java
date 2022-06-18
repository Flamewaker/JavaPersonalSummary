package com.todd.leetcode.offer2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 044. 二叉树每层的最大值
 * @date 5:41 PM 2022/6/17
 */
public class Offer044 {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            ans = Integer.MIN_VALUE;
            int cnt = queue.size();
            for (int i = 0; i < cnt; i++) {
                TreeNode curr = queue.poll();
                ans = Math.max(ans, curr.val);
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            }
            list.add(ans);
        }
        return list;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

