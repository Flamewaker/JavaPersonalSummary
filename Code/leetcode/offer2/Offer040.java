package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 040. 矩阵中最大的矩形
 * @date 4:11 PM 2022/6/17
 */
public class Offer040 {
    public int maximalRectangle(String[] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length();
        int area = 0;
        int[][][] dp = new int[m][n][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i].charAt(j) == '0') {
                    continue;
                }
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
