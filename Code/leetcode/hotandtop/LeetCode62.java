package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 62. 不同路径
 * 整体思路：
 * 简单dp
 * 1. dp[i][j] 指代到达 (i， j) 位置具有的路径情况
 * 2. dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 * @date 10:30 AM 2022/5/22
 */
public class LeetCode62 {
    public int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) {
            return m == 0 ? n : m;
        }
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j > 0 && i > 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                    // 注意边界情况
                } else if (j > 0) {
                    dp[i][j] = dp[i][j - 1];
                } else if (i > 0) {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public int uniquePathsSimplified(int m, int n) {
        if (m == 0 || n == 0) {
            return m == 0 ? n : m;
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
