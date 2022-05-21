package com.todd.leetcode.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 3. 无重复字符的最长子串
 * 整体思路：
 * 滑动窗口 : 用start记录滑动窗口的起始坐标，指针i指向当前窗口的end，当有重复字母的时候，
 * 若重复字母索引的下一个位置比滑动窗口要大，将start指针指向
 * 重复字母索引的下一个位置， 否则还是当前开始位置，这样计算的结果为end - start + 1。
 * @date 10:53 PM 2022/5/20
 */
public class LeetCode3 {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int n = s.length();
        // 1. 创建字符索引
        Map<Character, Integer> index = new HashMap<>();
        // 2. 遍历数组，移动指针
        int start = 0;
        int ans = 0;
        for (int end = 0; end < n; end++) {
            Integer charIndex = index.get(chars[end]);
            // 3. 判断边界指针的位置
            if (charIndex != null) {
                start = Math.max(charIndex + 1, start);
            }
            index.put(chars[end], end);
            ans = Math.max(ans, end - start + 1);
        }
        return ans;
    }
}
