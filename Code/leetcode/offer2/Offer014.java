package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 014. 字符串中的变位词
 * 整体思路：
 * 维护滑动窗口内的统计值，并进行比较
 * @date 2:53 PM 2022/6/15
 */
public class Offer014 {
    public boolean checkInclusion(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] target = new int[26];
        int[] curr = new int[26];
        for (int i = 0; i < n; i++) {
            target[s1.charAt(i) - 'a']++;
        }
        int left = 0;
        for (int right = 0; right < m; right++) {
            curr[s2.charAt(right) - 'a']++;
            if (right - left + 1 < n) {
                continue;
            }
            if (checkSame(curr, target)) {
                return true;
            }
            curr[s2.charAt(left++) - 'a']--;
        }
        return false;
    }

    private boolean checkSame(int[] curr, int[] target) {
        for (int i = 0; i < 26; i++) {
            if (curr[i] != target[i]) {
                return false;
            }
        }
        return true;
    }
}
