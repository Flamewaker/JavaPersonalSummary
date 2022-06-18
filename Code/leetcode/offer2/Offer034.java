package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 034. 外星语言是否排序
 * @date 9:56 AM 2022/6/17
 */
public class Offer034 {

    public boolean isAlienSorted(String[] words, String order) {
        int n = order.length();
        int[] map = new int[26];
        for (int i = 0; i < order.length(); i++) {
            map[order.charAt(i) - 'a'] = n - i;
        }
        for (int i = 1; i < words.length; i++) {
            if (!compare(words[i - 1], words[i], map)) {
                return false;
            }
        }
        return true;
    }

    private boolean compare(String a, String b, int[] map) {
        for (int i = 0; i < a.length() && i < b.length(); i++) {
            if (map[a.charAt(i) - 'a'] > map[b.charAt(i) - 'a']) {
                return true;
            } else if (map[a.charAt(i) - 'a'] < map[b.charAt(i) - 'a']) {
                return false;
            }
        }
        if (a.length() > b.length()) {
            return false;
        }
        return true;
    }
}
