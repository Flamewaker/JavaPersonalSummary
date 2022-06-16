package com.todd.leetcode.offer2;

import java.util.Arrays;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 012. 左右两边子数组的和相等
 * 整体思路：
 * 1. 用总的数组和total, 减去当前数组和sum, 如果差等于当前数组和减去当前值（sum-nums[i]）,则找到该坐标i
 * @date 10:12 AM 2022/6/15
 */
public class Offer012 {
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        int[] leftSum = new int[n];
        int[] rightSum = new int[n];
        leftSum[0] = 0;
        rightSum[n - 1] = 0;
        for (int i = 1; i < n; i++) {
            leftSum[i] += nums[i - 1] + leftSum[i - 1];
            rightSum[n - i - 1] = nums[n - i] + rightSum[n - i];
        }
        for (int i = 0; i < n; i++) {
            if (leftSum[i] == rightSum[i]) {
                return i;
            }
        }
        return -1;
    }

    public int pivotIndexOptimized(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            //总和减去当前子数组和等于当前子数组和减去当前值，则满足条件
            if (sum - nums[i] == total - sum) {
                return i;
            }
        }
        //若没有满足条件值，返回-1
        return -1;
    }

    public int pivotIndexOptimized2(int[] nums) {
        int total = Arrays.stream(nums).sum();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] == total - sum) {
                return i;
            }
            sum += nums[i];
        }
        return -1;
    }
}
