package com.todd.leetcode.normal;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 * BFS + 记录每层个数
 * @Author todd
 * @Date 2020/4/22
 */
public class LeetCode0199 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new LinkedList<>();
        }
        LinkedList<Integer> ans = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            ans.add(queue.peek().val);
            while(size != 0){
                TreeNode curNode = queue.poll();
                if(curNode.right != null) {
                    queue.offer(curNode.right);
                }
                if(curNode.left != null) {
                    queue.offer(curNode.left);
                }
                size--;
            }
        }
        return ans;
    }
}
