package com.todd.code;

/**
 * @author todd
 * @date 2020/6/27 16:53
 * @description: 给定一个未经排序的整数数组，找到最长且连续的的递增序列，并返回该序列的长度。
 * 简单题。
 */
public class LeetCode674 {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ans = 1;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                count++;
                ans = Math.max(ans, count);
            } else {
                count = 1;
            }
        }
        return ans;
    }
}
