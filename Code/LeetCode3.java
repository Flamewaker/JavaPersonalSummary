package com.todd;

import java.util.*;

/**
 * 滑动窗口
 *
 * @Auther todd
 * @Date 2020/4/13
 */
public class LeetCode3 {
    public int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (len == 0) {
            return 0;
        }
        int[] ascil = new int[256];
        int index = 0;
        int key = 0;
        int ans = 0;
        while (index < len) {
            char a = s.charAt(index);
            if (ascil[a] > 0) {
                key = Math.max(key, ascil[a]);
            }
            ascil[a] = index + 1;
            ans = Math.max(ans, index + 1 - key);
            index++;
        }
        return ans;
    }
}
