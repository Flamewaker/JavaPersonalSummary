package com.todd;

/**
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Offer48 {
    public int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (len == 0) {
            return 0;
        }
        int[] dict = new int[256];
        int index = 0;
        int key = 0;
        int ans = 0;
        while (index < len) {
            int a = s.charAt(index);
            if (dict[a] > 0) {
                key = Math.max(dict[a], key);
            }
            dict[a] = index + 1;
            ans = Math.max(ans, index + 1 - key);
            index++;
        }
        return ans;
    }
}
