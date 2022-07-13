package com.todd.leetcode.normal;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/8/16 9:13
 * @description: 290. 单词规律
 * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 *
 * 简单哈希表
 */
public class LeetCode0290 {
    public boolean wordPattern(String pattern, String str) {
        char[] patternStr = pattern.toCharArray();
        String[] strs = str.split(" ");
        int patternLen = patternStr.length;
        int wordLen = strs.length;
        if (patternLen != wordLen) {
            return false;
        }
        HashMap<Character, String> patternMap = new HashMap<>();
        for (int i = 0; i < patternLen; i++) {
            if (!patternMap.containsKey(patternStr[i])) {
                if (patternMap.containsValue(strs[i])) {
                    return false;
                }
                patternMap.put(patternStr[i], strs[i]);
            } else {
                if (!patternMap.get(patternStr[i]).equals(strs[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
