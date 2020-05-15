package com.todd;

/**
 * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
 * 字符串转换
 *
 * @Auther todd
 * @Date 2020/4/4
 */
public class LeetCode8 {
    public int myAtoi(String str) {
        char[] chars = str.toCharArray();
        int length = str.length();
        int idx = 0;
        while (idx < length && chars[idx] == ' ') {
            idx++;
        }
        if (idx == length) {
            return 0;
        }
        boolean negative = false;
        if (chars[idx] == '-') {
            negative = true;
            idx++;
        } else if (chars[idx] == '+') {
            idx++;
        } else if (!Character.isDigit(chars[idx])) {
            return 0;
        }
        int ans = 0;
        while (idx < length && Character.isDigit(chars[idx])) {
            int digit = chars[idx] - '0';
            if (ans > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + digit;
            idx++;
        }
        return negative ? -ans : ans;
    }
}
