package com.todd.leetcode.normal;

/**
 * 滑动窗口 : 用start记录滑动窗口的起始坐标，指针i指向当前窗口的end，当有重复字母的时候，若重复字母索引的下一个位置比滑动窗口要大，将start指针指向
 * 重复字母索引的下一个位置， 否则还是当前开始位置，这样计算的结果为end - start + 1。
 * @Author todd
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

    public int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[] last = new int[256];

        for (int i = 0; i < 256; i++) {
            last[i] = -1;
        }

        int left = 0;
        int ans = 0;
        for (int i = 0; i < len; i++) {
            char a = s.charAt(i);
            if (last[a] != -1) {
                left = Math.max(left, last[a] + 1);
            }
            last[a] = i;
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
