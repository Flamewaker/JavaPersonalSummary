package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 11:23
 * @description: 剑指 Offer 48. 最长不含重复字符的子字符串
 */
public class Offer48 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[] map = new int[128];
        for (int i = 0; i < 128; i++) {
            map[i] = -1;
        }

        int count = 0;
        int left = 0;
        for (int right = 0; right < len; right++) {
            if (map[s.charAt(right)] != -1) {
                left = Math.max(left, map[s.charAt(right)] + 1);
            }
            map[s.charAt(right)] = right;
            count = Math.max(count, right - left +  1);
        }
        return count;
    }
}
