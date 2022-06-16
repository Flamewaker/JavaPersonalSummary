package com.todd.leetcode.hotandtop;

import java.util.*;

/**
 * @author tongchengdong
 * @description 438. 找到字符串中所有字母异位词
 * 1. 我们可以先创建一个大小为 26 的数组 target 来统计字符串 p 的词频，另外一个同等大小的数组 cur 用来统计「滑动窗口」内的 s 的子串词频。
 * 当两个数组所统计词频相等，说明找到了一个异位组，将窗口的左端点加入答案。
 * @date 11:21 PM 2022/5/28
 */
public class LeetCode438 {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int n = s.length(), m = p.length();
        int[] cur = new int[26], target = new int[26];
        for (int i = 0; i < m; i++) {
            target[p.charAt(i) - 'a']++;
        }
        for (int left = 0, right = 0; right < n; right++) {
            cur[s.charAt(right) - 'a']++;
            if (right - left + 1 > m) {
               cur[s.charAt(left++) - 'a']--;
            }
            if (check(cur, target)) {
                ans.add(left);
            }
        }
        return ans;
    }
    boolean check(int[] c1, int[] c2) {
        for (int i = 0; i < 26; i++) {
            if (c1[i] != c2[i]) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> findAnagramsSimplified(String s, String p) {
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
            if (check(cur, target)) {
                ans.add(i - plen + 1);
            }
        }
        return ans;
    }
}
