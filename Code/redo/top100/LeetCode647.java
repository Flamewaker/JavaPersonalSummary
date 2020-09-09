package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/7 14:17
 * @description: 647. 回文子串
 */
public class LeetCode647 {
    public int countSubstrings(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int length = 0;
        boolean[][] dp = new boolean[length][length];
        int count = 0;
        for (int r = 0; r < length; r++) {
            for (int l = 0; l <= r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 1 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    count++;
                }
            }
        }
        return count;
    }
}
