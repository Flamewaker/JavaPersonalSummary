package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 437. 路径总和 III
 * 整体思路：
 * 1. 递归左右子树，在递归的子阶段中，继续以该节点为根节点继续进行路径的寻找 O(n2)
 * 2. 记录从根节点到该节点的路径和curr，在递归左右子树，看之前遍历过的路径中是否存在以curr - targetSum为路径和的路径，
 * 如果存在，那么从根节点到当前节点的路径,减去这条路径, 就是符合条件的路径之一。转化为了子问题。
 * @date 12:41 PM 2022/5/24
 */
public class LeetCode437 {

    int count = 0;
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        rootSum(root, targetSum);
        pathSum(root.left, targetSum);
        pathSum(root.right, targetSum);
        return count;
    }

    public void rootSum(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        if (root.val == targetSum) {
            count++;
        }
        int next = targetSum - root.val;
        rootSum(root.left, next);
        rootSum(root.right, next);
    }

    public int pathSumOptimized(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        // 1. 注意初始化
        Map<Integer, Integer> pathMap = new HashMap<>();
        pathMap.put(0, 1);
        // 2. 深搜
        searchRootSum(root, targetSum, 0, pathMap);
        return count;
    }

    public void searchRootSum(TreeNode root, int targetSum, int curr, Map<Integer, Integer> pathMap) {
        if (root == null) {
            return;
        }

        // sum - preSum = targetSum
        int val = root.val;
        curr += val;
        count += pathMap.getOrDefault(curr - targetSum, 0);

        pathMap.put(curr, pathMap.getOrDefault(curr, 0) + 1);
        searchRootSum(root.left, targetSum, curr, pathMap);
        searchRootSum(root.right, targetSum, curr, pathMap);
        pathMap.put(curr, pathMap.get(curr) - 1);
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
