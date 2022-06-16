package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 016. 不含重复字符的最长子字符串
 * 整体思路：
 * 滑动窗口，维护左指针和右指针
 * @date 12:04 AM 2022/6/16
 */
public class Offer016 {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int ans = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer index = map.get(s.charAt(i));
            if (index != null) {
                left = Math.max(index + 1, left);
            }
            ans = Math.max(ans, i - left + 1);
            map.put(s.charAt(i), i);
        }
        return ans;
    }
}
