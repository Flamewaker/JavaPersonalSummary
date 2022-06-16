package com.todd.leetcode.offer1;

/**
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
 * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
 * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer47 {
    public int maxValue(int[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int[][] dp = new int[rowLen][colLen];
        dp[0][0] = grid[0][0];
        for(int i = 1; i < rowLen; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for(int i = 1; i < colLen; i++) {
            dp[0][i] = dp[0][i - 1] +grid[0][i];
        }

        for(int i = 1; i < rowLen; i++) {
            for(int j = 1; j < colLen; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[rowLen - 1][colLen - 1];

    }
}
