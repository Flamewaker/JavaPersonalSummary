package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 78. 子集
 * 整体思路：简单dfs
 * @date 12:08 PM 2022/5/22
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
