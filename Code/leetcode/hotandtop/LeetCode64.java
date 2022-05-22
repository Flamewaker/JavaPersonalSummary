package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 64. 最小路径和
 * 整体思路：
 * dp模板题
 * 1. dp[i][j] 指代 (i,j) 位置的最小路径和
 * 2. dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
 * @date 10:42 AM 2022/5/22
 */
public class LeetCode64 {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}
