package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 020. 回文子字符串的个数
 * 推荐方法：
 * 1. dp[i][j] 记录 (i,j) 内是否是回文串
 * 2. 马拉车算法原来打ACM的时候还会，现在已经不会了，。
 * @date 11:30 AM 2022/6/16
 */
public class Offer020 {
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
