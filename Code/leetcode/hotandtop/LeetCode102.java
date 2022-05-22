package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author tongchengdong
 * @description 102. 二叉树的层序遍历
 * 整体思路：
 * 1. 简单队列，定义一个队列，每次获得当前层节点个数，进行遍历，并将下一层节点添加进去在下一轮进行遍历。
 * 2. 改进：用一个 num 记录每一层的数据个数，然后对该层节点进行遍历。这样不用每次获得栈的大小，LinkedList遍历时间为O(n)
 * @date 3:44 PM 2022/5/22
 */
public class LeetCode102 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offerLast(root);
        // 1. 定义当前层节点个数
        int num = 1;
        while (!queue.isEmpty()) {
            // 2. 对当前层进行遍历， 并统计下一层的个数
            int count = 0;
            List<Integer> levelAns = new ArrayList<>(num);
            for (int i = 0; i < num; i++) {
                TreeNode curr = queue.poll();
                levelAns.add(curr.val);
                if (curr.left != null) {
                    queue.offer(curr.left);
                    count++;
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                    count++;
                }
            }
            ans.add(levelAns);
            num = count;
        }
        return ans;
    }

    public List<List<Integer>> levelOrderSimplified(TreeNode root) {
        if(root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new LinkedList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()) {
            LinkedList<Integer> levelAns = new LinkedList<>();
            for(int i = q.size(); i > 0; i--) {
                TreeNode cur = q.poll();
                levelAns.add(cur.val);
                if(cur.left != null) {
                    q.offer(cur.left);
                }
                if(cur.right != null) {
                    q.offer(cur.right);
                }
            }
            ans.add(levelAns);
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
