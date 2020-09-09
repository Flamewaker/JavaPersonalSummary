package com.todd.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/23 16:18
 * @description: 子集
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 */
public class LeetCode78 {
    List<List<Integer>> ans = new LinkedList<>();
    List<Integer> temp = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backTrace(nums, 0, nums.length);
        return ans;
    }

    public void backTrace(int[] nums, int index, int length) {
        ans.add(new ArrayList<>(temp));
        for (int i = index; i < length; i++) {
            temp.add(nums[i]);
            backTrace(nums, i + 1, length);
            temp.remove(temp.size() - 1);
        }
    }
}
