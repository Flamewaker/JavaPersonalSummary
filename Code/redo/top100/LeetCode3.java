package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/6 15:06
 * @description: 3. 无重复字符的最长子串
 */
public class LeetCode3 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int ans = 0;
        int left = 0;
        int[] index = new int[128];
        for (int i = 0; i < 128; i++) {
            index[i] = -1;
        }
        for (int i = 0; i < len; i++) {
            if (index[s.charAt(i)] != -1) {
                left = Math.max(left, index[s.charAt(i)] + 1);
            }
            ans = Math.max(ans, i - left + 1);
            index[s.charAt(i)] = i;
        }
        return ans;
    }
}
