package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 050. 向下的路径节点之和
 * @date 7:07 PM 2022/6/17
 */
public class Offer050 {
    int count = 0;
    public int pathSum(TreeNode root, int targetSum) {
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
