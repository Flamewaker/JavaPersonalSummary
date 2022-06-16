package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 003. 前 n 个数字二进制中 1 的个数
 * 整体思路：
 * 1. 奇数 dp[i] = dp[i - 1] + 1
 * 2. 偶数 dp[i] = dp[i / 2]
 * @date 4:38 PM 2022/6/12
 */
public class Offer003 {
    public int[] countBits(int n) {
        if (n < 0) {
            return new int[0];
        }
        if (n == 0) {
            return new int[]{0};
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            if ((i & 1) == 1) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = dp[i >> 1];
            }
        }
        return dp;
    }
}
