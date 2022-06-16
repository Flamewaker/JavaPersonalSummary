package com.todd.leetcode.hotandtop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/7 14:50
 * @description: TODO
 */
public class LeetCode47 {
    List<List<Integer>> ans = new LinkedList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums.length == 0) {
            return ans;
        }
        boolean[] visited = new boolean[nums.length];
        getAllPermute(nums, 0,visited, new LinkedList<>());
        return ans;

    }
    public void getAllPermute(int[] nums, int n, boolean[] visited, LinkedList<Integer> curList) {
        if (n == nums.length) {
            ans.add(new LinkedList<>(curList));
        }
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i] && !set.contains(nums[i])) {
                set.add(nums[i]);
                visited[i] = true;
                curList.offerLast(nums[i]);
                getAllPermute(nums, n + 1, visited, curList);
                curList.pollLast();
                visited[i] = false;
            }
        }
    }
}
