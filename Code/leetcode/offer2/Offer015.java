package com.todd.leetcode.offer2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 015. 字符串中的所有变位词
 * @date 12:00 AM 2022/6/16
 */
public class Offer015 {
    public List<Integer> findAnagrams(String s, String p) {
        if (s == null || p == null || s.length() < p.length()) {
            return new ArrayList<>();
        }
        ArrayList<Integer> ans = new ArrayList<>();
        int slen = s.length();
        int plen = p.length();
        // 1. 定义遍历数组和目标数组
        int[] target = new int[26];
        int[] cur = new int[26];
        for (int i = 0; i < plen; i++) {
            target[p.charAt(i) - 'a']++;
        }
        // 2. 窗口遍历
        for (int i = 0; i < slen; i++) {
            // 2.1 访问过的字符累加
            cur[s.charAt(i) - 'a']++;
            // 2.2 判断窗口大小，左窗口向右移动
            if (i >= plen) {
                cur[s.charAt(i - plen) - 'a']--;
            }
            // 2.3 判断字母异位词条件
            if (campare(cur, target)) {
                ans.add(i - plen + 1);
            }
        }
        return ans;
    }

    private boolean campare(int[] a, int[] b) {
        for (int i = 0; i < 26; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
