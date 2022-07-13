package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/23 10:36
 * @description: 最小路径和
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小
 */
public class LeetCode0064 {
    public static void main(String[] args) {
        int[][] grid = new int[2][3];
        grid[0] = new int[]{1,2,5};
        grid[1] = new int[]{3,2,1};
        System.out.println(minPathSum(grid));
    }
    public static int minPathSum(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        System.out.println(m);
        System.out.println(n);
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0] ;
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[m - 1][n - 1];
    }
}
