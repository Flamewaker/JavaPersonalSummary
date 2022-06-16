package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 647. 回文子串
 * 整体思路：
 * 1. 按照长度遍历字符串，在遍历过程中记录子串是否是回文串
 * @date 1:18 PM 2022/6/7
 */
public class LeetCode647 {
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int cnt = 0;
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            cnt++;
        }
        for (int k = 1; k <= n - 1; k++) {
            for (int i = 0; i < n - k; i++) {
                if (s.charAt(i) == s.charAt(i + k) && (k <= 1 || dp[i + 1][i + k - 1])) {
                    dp[i][i + k] = true;
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
