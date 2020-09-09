package com.todd.code;

/**
 * @author todd
 * @date 2020/6/16 21:46
 * @description: 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
 * 1. 直接贪心枚举
 * 2. 动态规划
 * 
 */
public class LeetCode279 {
    int minCount = Integer.MAX_VALUE;
    public int numSquares(int n) {
        dfs(n, 0);
        return minCount;
    }

    public void dfs(int n, int count) {
        if (count >= minCount) {
            return;
        }
        if (n == 0) {
            minCount = Math.min(minCount, count);
        }
        for (int i = (int) Math.sqrt(n); i >= 1; i--) {
            if (i == 1) {
                minCount = Math.min(minCount, count + n);
            }
            dfs(n - i * i, count + 1);
        }

    }

    public int numSquares2(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

}
