package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 5. 最长回文子串
 * 整体思路：
 * 1. 动态规划
 * dp[i][j] = true 代指 [i,j] 所在子串为回文串
 * dp[i][j] = dp[i + 1][j - 1] == true && s[i] == s[j]
 * 时间复杂度 O(n2)
 * 2. 方法2.中心扩散法
 * 思路：分最长回文子串是奇数和偶数的情况，定义start为最长回文子串开始的索引，然后循环字符串，不断不断向外扩展回文字符串的长度，
 * 循环的过程中更新最大回文子串的长度和start的位置，最后返回start到start+ maxLength的子串就是本题的答案
 * 时间复杂度 O(n2)
 * @date 11:40 PM 2022/5/20
 */
public class LeetCode5 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int strLen = s.length();
        //最长回文串的起点
        int maxStart = 0;
        //最长回文串的终点
        int maxEnd = 0;
        //最长回文串的长度
        int maxLen = 1;

        boolean[][] dp = new boolean[strLen][strLen];

        for (int right = 1; right < strLen; right++) {
            for (int left = 0; left < right; left++) {
                if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || dp[left + 1][right - 1])) {
                    dp[left][right] = true;
                    if (right - left + 1 > maxLen) {
                        maxLen = right - left + 1;
                        maxStart = left;
                        maxEnd = right;

                    }
                }

            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    public String longestPalindromeOptimized(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        int n = s.length();
        int left = 0;
        int right = 0;
        int maxLen = 1;
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                if (s.charAt(i) == s.charAt(i + k) && (k <= 1 || dp[i + 1][i + k - 1])) {
                    dp[i][i + k] = true;
                    if (k + 1 > maxLen) {
                        left = i;
                        right = i + k;
                        maxLen = k + 1;
                    }
                }
            }
        }
        return s.substring(left, right + 1);
    }

    public String longestPalindromeCenterSpread(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int maxLen = 0;
        // 数组第一位记录起始位置，第二位记录长度
        int[] res = new int[2];
        for (int i = 0; i < s.length() - 1; i++) {
            int[] odd = centerSpread(s, i, i);
            int[] even = centerSpread(s, i, i + 1);
            int[] max = odd[1] > even[1] ? odd : even;
            if (max[1] > maxLen) {
                res = max;
                maxLen = max[1];
            }
        }
        return s.substring(res[0], res[0] + res[1]);
    }

    private int[] centerSpread(String s, int left, int right) {
        int len = s.length();
        while (left >= 0 && right < len) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        return new int[]{left + 1, right - left - 1};
    }

    /**
     * 把原来的字符串倒置了，找最长的公共子串就可以了
     * 申请一个二维的数组初始化为0，然后判断对应的字符是否相等,arr[i][j]=arr[i-1][j-1]+1
     * 当i=0或者j=0的时候单独分析，字符相等的话arr[i][j]就赋为1
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String origin = s;
        String reverse = new StringBuffer(s).reverse().toString();
        int length = s.length();
        int[][] arr = new int[length][length];
        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (origin.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i - 1][j - 1] + 1;
                    }
                }
                if (arr[i][j] > maxLen) {
                    maxLen = arr[i][j];
                    maxEnd = i;
                }

            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }
}
