package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 17:10
 * @description: 剑指 Offer 19. 正则表达式匹配
 */
public class Offer19 {
    public boolean isMatch(String s, String p) {
        if (s == null && p == null) {
            return false;
        }
        if (p.length() == 0) {
            return s.length() == 0;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        boolean[][] dp = new boolean[str.length + 1][pattern.length + 1];
        dp[0][0] = true;
        for (int i = 1; i <= pattern.length; i++) {
            if (pattern[i - 1] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i <= str.length; i++) {
            for (int j = 1; j <= pattern.length; j++) {
                if (pattern[j - 1] == str[i - 1] || pattern[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern[j - 1] == '*' && (pattern[j - 2] == str[i - 1] || pattern[j - 2] == '.')) {
                    dp[i][j] = dp[i][j - 1] || dp[i][j - 2] || dp[i - 1][j];
                } else if (pattern[j - 1] == '*') {
                    dp[i][j] = dp[i][j - 2];
                }
            }
        }

        return  dp[str.length][pattern.length];
    }

}
