package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 017. 含有所有字符的最短字符串
 * @date 12:23 AM 2022/6/16
 */
public class Offer017 {
    public String minWindow(String s, String t) {
        int[] mp = new int[256];
        for (char c : t.toCharArray()) {
            mp[c]++;
        }
        int n = s.length();
        int m = t.length();
        int start = 0, end = 0;
        int cnt = 0;
        int res = -1;
        String ans = "";
        while (end < n) {
            char a = s.charAt(end);
            if (mp[a] >= 1) {
                cnt++;
            }
            mp[a]--;
            while (cnt == m) {
                if (res == -1 || res > end - start + 1) {
                    ans = s.substring(start, end + 1);
                    res = end - start + 1;
                }
                char c = s.charAt(start);
                mp[c]++;
                if (mp[c] >= 1) {
                    cnt--;
                }
                start++;
            }
            end++;
        }
        return ans;
    }
}
