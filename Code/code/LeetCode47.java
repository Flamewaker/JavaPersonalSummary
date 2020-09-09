package com.todd.code;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/6/15 12:21
 * @description: 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 * 1. 利用HashSet进行去重。
 */
public class LeetCode47 {
    List<List<Integer>> ans = new LinkedList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
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
        HashSet<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!numSet.contains(nums[i]) && !used[i]) {
                numSet.add(nums[i]);
                used[i] = true;
                curPermutation.add(nums[i]);
                dfs(index + 1, used, nums, curPermutation);
                curPermutation.removeLast();
                used[i] = false;
            }
        }

    }
}
