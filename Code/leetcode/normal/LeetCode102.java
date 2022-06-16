package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author todd
 * @date 2020/6/15 22:18
 * @description: 二叉树的层序遍历
 */
public class LeetCode102 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
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
}
