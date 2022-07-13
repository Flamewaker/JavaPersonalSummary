package com.todd.leetcode.normal;

import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/6/15 11:49
 * @description: 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 * dfs回溯。注意需要记录每层状态信息。
 */
public class LeetCode0046 {
    List<List<Integer>> ans = new LinkedList<>();
    public List<List<Integer>> permute(int[] nums) {
        boolean[] usd = new boolean[nums.length];
        LinkedList<Integer> curPermutation = new LinkedList<>();
        dfs(0, usd, nums, curPermutation);
        return ans;
    }

    private void dfs(int index, boolean[] used, int[] nums, LinkedList<Integer> curPermutation) {
        if (index == nums.length) {
            ans.add((List<Integer>) curPermutation.clone());

            //res.add(new LinkedList<>(path));

            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                curPermutation.add(nums[i]);
                dfs(index + 1, used, nums, curPermutation);
                curPermutation.removeLast();
                used[i] = false;
            }
        }

    }

}
