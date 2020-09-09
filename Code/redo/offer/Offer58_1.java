package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 15:28
 * @description: 剑指 Offer 58 - I. 翻转单词顺序
 */
public class Offer58_1 {
    public String reverseWords(String s) {
        String[] words = s.trim().split("\\s+");
        StringBuilder str = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            str.append(words[i]);
            if(i != 0) {
                str.append(" ");
            }
        }
        return str.toString();
    }
}
