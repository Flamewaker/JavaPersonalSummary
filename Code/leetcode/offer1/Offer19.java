package com.todd.leetcode.offer1;

/**
 * 正则表达式匹配，递归方法，可以使用dp方法
 *
 * @Author todd
 * @Date 2020/4/15
 */
public class Offer19 {
    public boolean isMatch(String s, String p) {
        if (s == null && p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        return match(str, pattern, 0, 0);
    }

    public boolean match(char[] s, char[] p, int i, int j) {
        if (i == s.length && j == p.length) {
            return true;
        }
        if (i != s.length && j == p.length) {
            return false;
        }
        if (j < p.length - 1 && p[j + 1] == '*') {
            if (i < s.length && (p[j] == s[i] || p[j] == '.')) {
                return match(s, p, i + 1, j) || match(s, p, i + 1, j + 2) || match(s, p, i, j + 2);
            } else {
                return match(s, p, i, j + 2);
            }
        }
        if (i < s.length && j < p.length && (p[j] == s[i] || p[j] == '.')) {
            return match(s, p, i + 1, j + 1);
        }
        return false;
    }

    public boolean isMatch2(String s, String p) {
        if(s == null && p == null){
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        return match2(str, pattern);
    }

    private boolean match2(char[] str, char[] pattern) {
        if (pattern.length == 0) {
            return str.length == 0;
        }
        boolean[][] dp = new boolean[str.length + 1][pattern.length + 1];
        dp[0][0] = true;
        for (int i = 1; i <= pattern.length; i++) {
            if (pattern[i - 1] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i <= str.length; i++) {
            for (int j = 1; j <= pattern.length; j++) {
                if (str[i - 1] == pattern[j - 1] || pattern[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern[j - 1] == '*' && (str[i - 1] == pattern[j - 2] || pattern[j - 2] == '.')) {
                    dp[i][j] = dp[i][j - 2] || dp[i][j - 1] || dp[i - 1][j - 1];
                } else if (pattern[j - 1] == '*') {
                    dp[i][j] = dp[i][j - 2];
                }
            }
        }
        return dp[str.length][pattern.length];
    }


}
