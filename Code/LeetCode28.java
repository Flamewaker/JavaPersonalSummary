package com.todd;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现 strStr() 函数。kmp算法模板 O(m+n)。
 *
 * @Auther todd
 * @Date 2020/4/14
 */
public class LeetCode28 {
    public static void main(String[] args) {
        String str1 = "ABCDABCABCDABD";
        String str2 = "ABCDABD";
        strStr(str1, str2);
    }

    public static void strStr(String haystack, String needle) {
        char[] str1 = haystack.toCharArray();
        char[] str2 = needle.toCharArray();
        int sLen = str1.length;
        int pLen = str2.length;
        ArrayList<Integer> ans = new ArrayList<>();
        int[] nxt = build(str2);

        for (int i = 0, j = 0; i < sLen; i++) {
            while (j > 0 && str1[i] != str2[j]) {
                j = nxt[j];
            }
            if (str1[i] == str2[j]) {
                j++;
            }
            if (j == pLen) {
                ans.add(i - str2.length + 1);
                j = nxt[j];
            }
        }
        System.out.println(ans.toString());
    }

    public static int[] build(char[] str) {
        int[] nxt = new int[str.length + 1];
        for (int i = 1, j = 0; i < str.length; i++) {
            while (j > 0 && str[i] != str[j]) {
                j = nxt[j];
            }
            if (str[i] == str[j]) {
                j++;
            }
            nxt[i + 1] = j;
        }
        return nxt;
    }
}
