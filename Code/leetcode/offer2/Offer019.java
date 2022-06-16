package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 019. 最多删除一个字符得到回文
 * 整体思路：
 * 首尾双指针，不匹配时考虑两种情况：删除当前左指针的字符、删除当前右指针的字符，两种情况有其一满足即可，均不满足则返回false。
 * @date 11:13 AM 2022/6/16
 */
public class Offer019 {
    public boolean validPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return valid(s, left, right - 1) || valid(s, left + 1, right);
            }
        }
        return true;
    }

    public boolean valid(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
