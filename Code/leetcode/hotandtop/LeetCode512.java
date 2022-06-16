package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 516. 最长回文子序列
 * 整体思路：
 * 1. dp[i][j] = dp[i + 1][j - 1] + 2 s(i) = s(j)
 * 2. dp[i][j] = dp[i + 1][j] or dp[i][j - 1]
 *
 * @date 12:09 PM 2022/6/5
 */
public class LeetCode512 {
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; j++) {
            dp[j][j] = 1;
            for (int i = j - 1; i >= 0; i--) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }
}
