package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/27 12:00
 * @description: 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。换句话说，第一个字符串的排列之一是第二个字符串的子串。
 * 1. 暴力搜索 (使用数组) O(l1 + 26 ∗ (l2 − l1))
 * 2. 滑动窗口 (使用数组) O(l1 + (2 * l2 - l1))
 */
public class LeetCode567 {
    public boolean checkInclusion(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int lengthS1 = str1.length;
        int[] countStr1 = new int[26];
        for (int i = 0; i < lengthS1; i++) {
            countStr1[str1[i] - 'a']++;
        }

        for (int i = 0; i < str2.length - lengthS1 + 1; i++) {
            int[] temp = countStr1.clone();
            for (int j = i; j < i + lengthS1; j++) {
                if (temp[str2[j] - 'a'] > 0) {
                    temp[str2[j] - 'a']--;
                } else {
                    break;
                }
                if (j == i + lengthS1 - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkInclusion2(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] countStr1 = new int[26];
        for (int i = 0; i < str1.length; i++) {
            countStr1[str1[i] - 'a']++;
        }
        int left = 0;
        int right = 0;
        while (right < str2.length) {
            if (countStr1[str2[right] - 'a'] > 0) {
                countStr1[str2[right] - 'a']--;
                if (right - left + 1 == str1.length) {
                    return true;
                }
                right++;

            } else {
                while (left < right && countStr1[str2[right] - 'a'] <= 0) {
                    countStr1[str2[left] - 'a']++;
                    left++;
                }
                if (left == right && countStr1[str2[right] - 'a'] <= 0) {
                    left++;
                    right++;
                }
            }

        }

        return false;
    }

    public boolean checkInclusion3(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] countStr1 = new int[26];
        for (int i = 0; i < str1.length; i++) {
            countStr1[str1[i] - 'a']++;
        }
        int left = 0;
        int right = 0;
        while (right < str2.length) {
            char x = str2[right++];
            countStr1[x - 'a']--;
            while (left < right && countStr1[x - 'a'] < 0) {
                countStr1[str2[left++] - 'a']++;
            }
            if(right - left == str1.length) {
                return true;
            }
        }

        return false;
    }
}
