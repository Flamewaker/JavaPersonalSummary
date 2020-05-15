package com.todd;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。
 * 1. 遍历字符串 s ，使用哈希表统计 “各字符数量是否 > 1>1 ”。
 * 2. 再遍历字符串 s ，在哈希表中找到首个 “数量为 1的字符”，并返回。
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Offer50 {
    public char firstUniqChar(String s) {
        char[] num = new char[256];
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            num[str[i]]++;
        }
        for (int i = 0; i < str.length; i++) {
            if (num[str[i]] == 1) {
                return str[i];
            }
        }
        return ' ';
    }

    public char firstUniqChar2(String s) {
        HashMap<Character, Integer> dic = new HashMap<>();
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            dic.put(str[i], dic.getOrDefault(str[i], 0) + 1);
        }
        for (int i = 0; i < str.length; i++) {
            if (dic.get(str[i]).equals(1)) {
                return str[i];
            }
        }
        return ' ';
    }

    public char firstUniqChar3(String s) {
        HashMap<Character, Integer> dic = new LinkedHashMap<>();
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            dic.put(str[i], dic.getOrDefault(str[i], 0) + 1);
        }
        for (Map.Entry<Character, Integer> characterIntegerEntry : dic.entrySet()) {
            if (characterIntegerEntry.getValue().equals(1)) {
                return characterIntegerEntry.getKey();
            }
        }
        return ' ';
    }
}
