package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * @author tongchengdong
 * @description 628. 三个数的最大乘积
 * 整体思路
 * （最少要有三个数）
 * 都为正数：乘积最大值为排序数组最后三个数相乘
 * 都为负数：乘积最大值为排序数组最后三个数相乘
 * 有正有负：（1） 乘积最大值为排序数组最后三个数相乘
 * （2） 乘积最大值为排序数组前两个负数与数组最后一个正数相乘
 * 1. 排序算法： O(nlogn)
 * 2. 一边扫描： O(n)
 * @date 2:28 PM 2022/6/4
 */
public class LeetCode0628 {
    public int maximumProduct(int[] nums) {
        if (nums == null || nums.length <= 2) {
            return 0;
        }
        Arrays.sort(nums);
        int n = nums.length;
        return Math.max(nums[0] * nums[1] * nums[n - 1], nums[n - 3] * nums[n - 2] * nums[n - 1]);
    }

    public int maximumProductOptimized(int[] nums) {
        if (nums == null || nums.length <= 2) {
            return 0;
        }
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > max3) {
                max3 = num;
            }
            if (max3 > max2) {
                int temp = max3;
                max3 = max2;
                max2 = temp;
            }
            if (max2 > max1) {
                int temp = max1;
                max1 = max2;
                max2 = temp;
            }
            if (num < min2) {
                min2 = num;
            }
            if (min2 < min1) {
                int temp = min2;
                min2 = min1;
                min1 = temp;
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }
}
