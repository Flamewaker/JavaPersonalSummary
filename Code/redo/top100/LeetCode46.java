package com.todd.redo.top100;

import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/7 14:38
 * @description: 46. 全排列
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
            ans.add((LinkedList<Integer>) curList.clone());
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
