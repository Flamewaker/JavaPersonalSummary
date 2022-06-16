package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/5/19 8:29
 * @description: 验证回文串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 */
public class LeetCode125 {
    public static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toLowerCase().toCharArray();
        int left = 0;
        int right = str.length - 1;
        while (left < right) {
            if (str[left] == str[right]) {
                left++;
                right--;
            } else if (!Character.isLetterOrDigit(str[left])) {
                left++;
            } else if (!Character.isLetterOrDigit(str[right])) {
                right--;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("0P"));
    }
}
