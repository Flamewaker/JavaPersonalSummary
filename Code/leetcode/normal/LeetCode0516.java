package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/27 11:16
 * @description: 给定一个字符串 s ，找到其中最长的回文子序列，并返回该序列的长度。可以假设 s 的最大长度为 1000 。
 * 如果 s 的第 i 个字符和第 j 个字符相同的话
 * f[i][j] = f[i + 1][j - 1] + 2
 * 如果 s 的第 i 个字符和第 j 个字符不同的话
 * f[i][j] = max(f[i + 1][j], f[i][j - 1])
 * 然后注意遍历顺序，i 从第一个字符开始往后遍历，j 从 i - 1 开始往前遍历，这样可以保证每个子问题都已经算好了。
 */
public class LeetCode0516 {
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int length = s.length();
        char[] str = s.toCharArray();
        int dp[][] = new int[length][length];

        for (int i = 0; i < length; i++) {
            dp[i][i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (str[j] == str[i]) {
                    dp[j][i] = dp[j + 1][i - 1] + 2;
                } else {
                    dp[j][i] = Math.max(dp[j][i - 1], dp[j + 1][i]);
                }
            }
        }
        return dp[0][length - 1];
    }
}
