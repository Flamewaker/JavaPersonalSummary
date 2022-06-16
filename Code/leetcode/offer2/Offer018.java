package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 018. 有效的回文
 * @date 11:11 AM 2022/6/16
 */
public class Offer018 {
    public boolean isPalindrome(String s) {
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
}
