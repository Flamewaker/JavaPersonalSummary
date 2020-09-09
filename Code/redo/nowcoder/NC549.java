package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/5 10:21
 * @description: 流浪者与宝藏
 * 牛牛流浪大陆的时候偶然间发现了一个地图和k把钥匙，地图上标出了一些宝藏的位置。
 * 一把钥匙可以开启一个宝藏，获得该宝藏里面的金币，钥匙使用后会被损坏无法再次使用，一个位置(i,j)可能有多个宝藏，但是一个位置只能使用一把钥匙。
 * 因为牛牛是一个精通传送术的流浪者，所以他一步可以走很远。但是他不会走回头路，也不会在一块地方停留。
 * 换言之，如果牛牛当前位于第i行第j列，那么他下一步的位置的行号的范围是[i+1,inf),列号的范围是[j+1,inf)。inf代表无穷大
 * 牛牛最开始位于位置(0,0)
 * 现在牛牛想知道，他最多可以获得多少金币。
 *
 * 简单dp问题
 */
public class NC549 {
    public int solve (int n, int k, int[] x, int[] y, int[] a) {
        // write code here
        int[][] dp = new int[501][501];
        int[][] T = new int [501][501];
        for (int i = 0; i < n; i++) {
            T[x[i]][y[i]] = Math.max(a[i], T[x[i]][y[i]]);
        }
        for (int m = 1; m < k + 1; m++) {
            int[][] dp2 = new int[501][501];
            for (int i = 1; i < 501; i++) {
                for (int j = 1; j < 501; j++) {
                    dp2[i][j] = Math.max(dp[i - 1][j - 1] + T[i][j], Math.max(dp2[i - 1][j], dp2[i][j - 1]));
                }
            }
            dp = dp2;
        }
        return dp[500][500];
    }
}
