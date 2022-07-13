package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/16 9:14
 * @description: 10. 正则表达式匹配
 *
 * 状态 dp
 * dp[i][j] 表示 s 的前 i 个是否能被 p 的前 j 个匹配
 * 1. 如果 p.charAt(j) == s.charAt(i) : dp[i][j] = dp[i-1][j-1]；
 * 2. 如果 p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1]；
 * 3. 如果 p.charAt(j) == '*'：
 *      1. 如果 p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2] //in this case, a* only counts as empty
 *      2. 如果 p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.'：
 *          dp[i][j] = dp[i-1][j] //in this case, a* counts as multiple a
 *          or dp[i][j] = dp[i][j-1] // in this case, a* counts as single a
 *          or dp[i][j] = dp[i][j-2] // in this case, a* counts as empty
 *
 */
public class LeetCode0010 {

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        // 初始化dp[0][j]表示p[1:j]与空串的匹配情况
        for (int i = 1; i <= p.length(); i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*' && (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1))){
                    dp[i][j] = dp[i - 1][j - 1] || dp[i - 1][j] || dp[i][j - 2];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2];
                }
            }
        }

        return dp[s.length()][p.length()];
    }
}
