package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 113. 路径总和 II
 * 整体思路：
 * dfs
 * @date 12:23 PM 2022/5/24
 */
public class LeetCode113 {

    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return ans;
        }
        searchPath(root, targetSum, new ArrayList<>());
        return ans;
    }

    public void searchPath(TreeNode root, int targetSum, List<Integer> path) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        int next = targetSum - root.val;
        if (next == 0 && root.left == null && root.right == null) {
            ans.add(new ArrayList<>(path));
        }
        searchPath(root.left, next, path);
        searchPath(root.right, next, path);
        path.remove(path.size() - 1);
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
