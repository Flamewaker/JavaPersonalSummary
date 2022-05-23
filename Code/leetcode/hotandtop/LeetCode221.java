package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 221. 最大正方形
 * 整体思路：
 * 1. dp[i][j] 是代表的以坐标点 (i,j) 为右下角的最大正方形的边长
 * 2. dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1 木桶的短板效应
 * 3. 暴力算法：遍历矩阵中的每个元素，每次遇到 1，则将该元素作为正方形的左上角，确定正方形的左上角后，根据左上角所在的行和列计算可能的最大正方形的边长，
 * 每次在下方新增一行以及在右方新增一列，判断新增的行和列是否满足所有元素都是 1。；
 * @date 11:11 AM 2022/5/23
 */
public class LeetCode221 {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int ans = 0;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        return ans * ans;
    }
}
