package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 11:29
 * @description: 剑指 Offer 49. 丑数
 */
public class Offer49 {
    public int nthUglyNumber(int n) {
        int p2 = 0, p3 = 0, p5 = 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int next = Math.min(dp[p2] * 2, Math.min(dp[p3] * 3, dp[p5] * 5));
            dp[i] = next;

            if (next == dp[p2] * 2) {
                p2++;
            }
            if (next == dp[p3] * 3) {
                p3++;
            }
            if (next == dp[p5] * 5) {
                p5++;
            }
        }
        return dp[n];
    }
}
