package com.todd.leetcode.normal;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author todd
 * @date 2020/6/16 15:38
 * @description: 给定一个字符串，逐个翻转字符串中的每个单词。
 * 调用API或直接使用双指针从后往前遍历。
 */
public class LeetCode151 {
    public String reverseWords(String s) {
        s.trim(); // 一定要做
        String[] words = s.split("\\s+");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }

    public String reverseWords2(String s) {
        StringBuilder str = new StringBuilder();
        int end = s.length();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                end = i;
                continue;
            }
            if (i == 0 || s.charAt(i - 1) == ' ') {
                str.append(s.substring(i, end));
                str.append(" ");
                end = i;
            }
        }
        return str.toString().trim();
    }
}
