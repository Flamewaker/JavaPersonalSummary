package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/8/24 9:19
 * @description: 132. 分割回文串 II
 * 区间DP
 */
public class LeetCode132 {
    public int minCut(String s) {
        int length = s.length();
        boolean[][] range = new boolean[length][length];
        int[] dp = new int[length];

        for (int i = 0; i < length; i++) {
            dp[i] = i;
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i) && (i - j < 2 || range[j + 1][i - 1])) {
                    range[j][i] = true;
                    dp[i] = j == 0 ? 0 : Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[length - 1];
    }
}
