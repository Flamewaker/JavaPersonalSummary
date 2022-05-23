package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 152. 乘积最大子数组
 * 整体思路：
 * 1. dp[i][0] 指代以 nums[i] 为结尾的乘积最大子数组的乘积
 * 2. dp[i][1] 指代以 nums[i] 为结尾的乘积最小子数组的乘积
 * @date 12:14 AM 2022/5/23
 */
public class LeetCode152 {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[][] dp = new int[nums.length][2];
        dp[0][0] = dp[0][1] = nums[0];
        int ans = nums[0];
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = Math.max(nums[i], Math.max(dp[i - 1][0] * nums[i], dp[i - 1][0] * nums[i]));
            dp[i][1] = Math.min(nums[i], Math.min(dp[i - 1][0] * nums[i], dp[i - 1][0] * nums[i]));
            ans = Math.max(ans, dp[i][0]);
        }
        return ans;
    }

    public int maxProductOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int ans = nums[0];
        int iMax = nums[0];
        int iMin = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int mx = iMax, mn = iMin;
            iMax = Math.max(nums[i], Math.max(nums[i] * mx, nums[i] * mn));
            iMin = Math.min(nums[i], Math.min(nums[i] * mx, nums[i] * mn));
            ans = Math.max(ans, iMax);
        }
        return ans;
    }
}
