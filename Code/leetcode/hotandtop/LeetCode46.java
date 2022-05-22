package com.todd.leetcode.hotandtop;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 46. 全排列
 * 整体思路：
 * 1. 简单dfs
 * 2. 注意记录已经访问过的数字
 * @date 11:46 PM 2022/5/21
 */
public class LeetCode46 {
    List<List<Integer>> ans = new LinkedList<>();
    public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0) {
            return ans;
        }
        boolean[] visited = new boolean[nums.length];
        getAllPermute(nums, 0,visited, new LinkedList<>());
        return ans;

    }
    public void getAllPermute(int[] nums, int n, boolean[] visited, LinkedList<Integer> curList) {
        if (n == nums.length) {
            ans.add((List<Integer>) curList.clone());
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                curList.offerLast(nums[i]);
                getAllPermute(nums, n + 1, visited, curList);
                curList.pollLast();
                visited[i] = false;
            }
        }
    }
}
