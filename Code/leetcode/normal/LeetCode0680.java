package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/5/19 8:01
 * @description: 验证回文字符串 Ⅱ
 * 给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串
 * 思路：贪心算法，使用双指针
 */
public class LeetCode0680 {
    public boolean validPalindrome(String s) {
        if (s.length() == 0) {
            return false;
        }
        char[] str = s.toCharArray();
        return validString(str, 0, str.length - 1, 0);
    }

    private boolean validString(char[] str, int left, int right, int count) {
        while (left < right) {
            if (str[left] == str[right]) {
                left++;
                right--;
            } else if (count < 1) {
                return validString(str, left + 1, right, count + 1) || validString(str, left, right - 1, count + 1);
            } else {
                return false;
            }
        }
        return true;
    }
}
