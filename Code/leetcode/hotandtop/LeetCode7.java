package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/8/6 21:58
 * @description: 7. 整数反转
 */
public class LeetCode7 {
    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            int re = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || ans < Integer.MIN_VALUE / 10) {
                return 0;
            }
            x /= 10;
            ans = ans * 10 + x;
        }
        return ans;
    }
}
