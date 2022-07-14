package com.todd.leetcode.normal;

import java.util.*;

/**
 * @author tongchengdong
 * @description TODO
 * @date 5:43 PM 2022/7/13
 */
public class LeetCode0742 {
    HashMap<TreeNode, List<TreeNode>> graph = new HashMap<>();
    TreeNode start;

    public int findClosestLeaf(TreeNode root, int k) {
        if (root == null) {
            return 0;
        }
        buildGraph(root, null, k);
        // 通过BFS进行搜索
        LinkedList<TreeNode> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offerLast(start);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode curr = queue.pollFirst();
                visited.add(curr.val);
                if (curr.left == null && curr.right == null) {
                    return curr.val;
                }
                List<TreeNode> list = graph.get(curr);
                for (TreeNode next : list) {
                    if (!visited.contains(next.val)) {
                        queue.offerLast(next);
                    }
                }
                size--;
            }
        }
        return 0;
    }

    private void buildGraph(TreeNode curr, TreeNode parent, int k) {
        if (curr == null) {
            return;
        }
        // 记录开始遍历节点
        if (curr.val == k) {
            start = curr;
        }
        // 构建 Graph
        if (parent != null) {
            if (!graph.containsKey(curr)) {
                graph.put(curr, new LinkedList<>());
            }
            if (!graph.containsKey(parent)) {
                graph.put(parent, new LinkedList<>());
            }
            graph.get(curr).add(parent);
            graph.get(parent).add(curr);
        }
        buildGraph(curr.left, curr, k);
        buildGraph(curr.right, curr, k);
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
