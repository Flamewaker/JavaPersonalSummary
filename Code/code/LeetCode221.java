package com.todd.code;

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * @author todd
 * @date 2020/7/15 13:58
 * @description: 221. 最大正方形
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * 1. 暴力
 * 2. 动态规划
 *
 */
public class LeetCode221 {
    public int maximalSquare(char[][] matrix) {
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    max = Math.max(max, function(matrix, i, j));
                }
            }
        }
        return max;
    }

    public int function(char[][] matrix, int i, int j) {
        int m = 0;
        for (; m + j < matrix[0].length && m + i < matrix.length; m++) {
            for (int p = j; p <= j + m; p++) {
                if (matrix[m+i][p] != '1') {
                    return m * m;
                }
            }

            for (int q = i; q < i + m; q++) {
                if (matrix[q][m+j] != '1') {
                    return m * m;
                }
            }

        }
        return m * m;
    }

    public int maximalSquare2(char[][] matrix) {
        /**
         * dp[i][j]表示以第i行第j列为右下角所能构成的最大正方形边长。
         * 如果该位置的值是 0，则 dp(i,j) = 0，因为当前位置不可能在由 1 组成的正方形中；
         * 如果该位置的值是 1，则 dp(i,j) 的值由其上方、左方和左上方的三个相邻位置的 dp 值决定。具体而言，当前位置的元素值等于三个相邻位置的元素中的最小值加 1；
         * 则递推式为:
         * dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]);
         */
        int m = matrix.length;
        if (m < 1) {
            return 0;
        }
        int n = matrix[0].length;
        int max = 0;
        int[][] dp = new int[m+1][n+1];

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max * max;
    }
}
