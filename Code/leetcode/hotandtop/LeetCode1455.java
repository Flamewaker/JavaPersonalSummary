package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/9/2 9:26
 * @description: 检查单词是否为句中其他单词的前缀
 * 给你一个字符串 sentence 作为句子并指定检索词为 searchWord ，其中句子由若干用 单个空格 分隔的单词组成。
 *
 * 请你检查检索词 searchWord 是否为句子 sentence 中任意单词的前缀。
 *
 * 如果 searchWord 是某一个单词的前缀，则返回句子 sentence 中该单词所对应的下标（下标从 1 开始）。
 * 如果 searchWord 是多个单词的前缀，则返回匹配的第一个单词的下标（最小下标）。
 * 如果 searchWord 不是任何单词的前缀，则返回 -1 。
 * 字符串 S 的 「前缀」是 S 的任何前导连续子字符串。
 *
 * 写一个判断前缀的函数，或者直接使用String的startsWith()方法。
 *
 */
public class LeetCode1455 {
    public int isPrefixOfWord(String sentence, String searchWord) {
        if (sentence == null || searchWord == null || sentence.length() == 0 || searchWord.length() == 0) {
            return  -1;
        }
        String[] strs = sentence.split(" ");
        for (int i = 0; i < strs.length; i++) {

            if (startWith(strs[i], searchWord)) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean startWith(String a, String b) {
         int lenA = a.length();
         int lenB = b.length();
         if (lenA < lenB) {
             return false;
         }
         int i = 0, j = 0;
         while (j < lenB) {
             if (a.charAt(i++) != b.charAt(j++)) {
                 return false;
             }
         }
         return true;
    }
}
