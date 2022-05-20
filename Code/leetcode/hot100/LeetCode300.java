package com.todd.leetcode.hot100;

/**
 * @author tongchengdong
 * @description 300. 最长递增子序列
 * 简单dp
 * 整体思路：
 * 1. dp[i] 指代 [0, i] 以 nums[i] 结尾的最长递增子序列个数
 * 2. dp[i] = Math.max(dp[i], dp[j] + 1) if (nums[i] > nums[j])
 */
public class LeetCode300 {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 1. 初始化, 最小值为1
            dp[i] = 1;
            // 2. 计算以 nums[j] 为倒数第二个递增数的最长子序列
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // 3. dp[i]计算完毕统计当前结果
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
