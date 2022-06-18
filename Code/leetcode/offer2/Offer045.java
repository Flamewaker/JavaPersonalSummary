package com.todd.leetcode.offer2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 045. 二叉树最底层最左边的值
 * @date 5:53 PM 2022/6/17
 */
public class Offer045 {

    int height = 0;
    TreeNode max = null;

    public int findBottomLeftValue(TreeNode root) {
        dfs(root, 1);
        return max.val;
    }

    public void dfs(TreeNode root, int h) {
        if (root == null) {
            return;
        }
        if (h > height) {
            height = h;
            max = root;
        }
        dfs(root.left, h + 1);
        dfs(root.right, h + 1);
    }

    public int findBottomLeftValueBfs(TreeNode root) {
        int ans = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i = 0; i < s; i++) {
                TreeNode cur = queue.poll();
                if (i == 0) {
                    ans = cur.val;
                }
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return ans;
    }

    public class TreeNode {
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
