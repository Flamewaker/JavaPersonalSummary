package com.todd.leetcode.hot100;

/**
 * @author tongchengdong
 * @description LeetCode53. 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 简单dp
 * 整体思路：
 * 定义dp[i]为以nums[i]结尾的连续子数组的最大和
 * 1. dp[i] = dp[i - 1] + nums[i] if(dp[i - 1] > 0)
 * 2. dp[i] = nums[i]             if(dp[i - 1] < 0)
 * ===> dp[i] = max(nums[i], dp[i - 1] + nums[i])
 */
public class LeetCode53 {

    /**
     * 状态转移
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < len; i++) {
            // 1. 子问题的状态转移
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            // 2. 求解最大和
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * 简化过程
     */
    public int maxSubArraySimplified(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int last = nums[0];
        int ans = nums[0];
        for (int i = 1; i < len; i++) {
            // 1. 子问题的状态转移
            last = Math.max(nums[i], nums[i] + last);
            // 2. 求解最大和
            ans = Math.max(ans, last);
        }
        return ans;
    }

    /**
     * 超时答案
     */
    public int maxSubArrayTimeOut(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        int len = nums.length;
        int[] sum = new int[len + 1];
        sum[0] = 0;
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                ans = Math.max(ans, sum[j] - sum[i]);
            }
        }
        return ans;
    }
}
