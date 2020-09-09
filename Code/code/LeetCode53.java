package com.todd.code;

/**
 * @author todd
 * @date 2020/6/8 10:22
 * @description: 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 */
public class LeetCode53 {
    public int maxSubArray(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int max = nums[0];
        int subMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //负增益
            if (subMax < 0) {
                subMax = nums[i];
            } else { //正增益
                subMax += nums[i];
            }
            max = Math.max(subMax, max);
        }
        return max;
    }
}
