package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/6 21:47
 * @description: 5. 最长回文子串
 */
public class LeetCode5 {
    public String longestPalindrome(String s) {
        if (s.length() == 0) {
            return "";
        }
        int length = s.length();
        int start = 0;
        int end = 0;
        int maxLen = 0;
        boolean[][] dp = new boolean[length][length];
        for (int r = 0; r < s.length(); r++) {
            for (int l = 0; l <= r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 1 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (maxLen < r - l + 1) {
                        start = l;
                        end = r;
                        maxLen = r - l + 1;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }
}
