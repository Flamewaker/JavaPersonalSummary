package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 70. 爬楼梯
 * 整体思路：
 * 简单dp
 * dp[i] = dp[i - 1] + dp[i - 2]
 * @date 11:29 AM 2022/5/22
 */
public class LeetCode70 {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int last = 1;
        int curr = 2;
        for (int i = 3; i <= n; i++) {
            int temp = last + curr;
            last = curr;
            curr = temp;
        }
        return curr;
    }

    public int climbStairsOriginal(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
