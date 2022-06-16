package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 013. 二维子矩阵的和
 * 整体思路：
 * 二维空间的前缀和 preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i][j]
 * @date 11:31 AM 2022/6/15
 */
public class Offer013 {

    class NumMatrix {
        private int[][] preSum;

        public NumMatrix(int[][] matrix) {
            int n = matrix.length, m = n == 0 ? 0 : matrix[0].length;
            preSum = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            row1++; col1++; row2++; col2++;
            return preSum[row2][col2] + preSum[row1 - 1][col1 - 1] - preSum[row1 - 1][col2] - preSum[row2][col1 - 1];
        }
    }
}
