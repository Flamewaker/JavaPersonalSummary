package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 5. 最长回文子串
 * 整体思路：
 * dp[i][j] = true 代指 [i,j] 所在子串为回文串
 * dp[i][j] = dp[i + 1][j - 1] == true && s[i] == s[j]
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

    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        //保存起始位置，测试了用数组似乎能比全局变量稍快一点
        int[] range = new int[2];
        char[] str = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            //把回文看成中间的部分全是同一字符，左右部分相对称
            //找到下一个与当前字符不同的字符
            i = findLongest(str, i, range);
        }
        return s.substring(range[0], range[1] + 1);
    }

    public static int findLongest(char[] str, int low, int[] range) {
        //查找中间部分
        int high = low;
        while (high < str.length - 1 && str[high + 1] == str[low]) {
            high++;
        }
        //定位中间部分的最后一个字符
        int ans = high;
        //从中间向左右扩散
        while (low > 0 && high < str.length - 1 && str[low - 1] == str[high + 1]) {
            low--;
            high++;
        }
        //记录最大长度
        if (high - low > range[1] - range[0]) {
            range[0] = low;
            range[1] = high;
        }
        return ans;
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
