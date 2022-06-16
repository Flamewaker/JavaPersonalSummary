package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 85. 最大矩形
 * 整体思路：
 * 1. 动态规划：dp[i][j][2] 代表以 (i, j) 为右下角顶点的最大矩形。第一维为最大横长，第二维为最大竖长。
 * @date 8:49 AM 2022/6/5
 */
public class LeetCode85 {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int area = 0;
        int[][][] dp = new int[m][n][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') continue;
                if (i == 0 && j == 0) {
                    dp[i][j][0] = dp[i][j][1] = 1;
                } else if (i == 0) {
                    dp[i][j][0] = dp[i][j - 1][0] + 1;
                    dp[i][j][1] = 1;
                } else if (j == 0) {
                    dp[i][j][0] = 1;
                    dp[i][j][1] = dp[i - 1][j][1] + 1;
                } else {
                    dp[i][j][0] = dp[i][j - 1][0] + 1;
                    dp[i][j][1] = dp[i - 1][j][1] + 1;
                }
                for (int w = 1, h = dp[i][j][1]; w <= dp[i][j][0]; w++) {
                    h = Math.min(h, dp[i][j + 1 - w][1]);
                    area = Math.max(area, w * h);
                }
            }
        }
        return area;
    }
}
