package com.todd.leetcode.normal;

/**
 * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
 * 字符串转换
 *
 * 1. 去掉前导空格
 * 2. 再是处理正负号
 * 3. 识别数字，注意越界情况。
 * 这道题目如果只是简单地字符串转整数的话，就是简单地 ans = ans * 10 + digit。
 * 但是注意这道题目可能会超过integer的最大表示！
 * 也就是说会在某一步 ans * 10 + digit > Integer.MAX_VALUE。
 * *10 和 +digit 都有可能越界，那么只要把这些都移到右边去就可以了。
 * ans > (Integer.MAX_VALUE - digit) / 10 就是越界。
 *
 * @Author todd
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
                // 本来应该是 ans * 10 + digit > Integer.MAX_VALUE
                // 但是 *10 和 + digit 都有可能越界，所有都移动到右边去就可以了。
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + digit;
            idx++;
        }
        return negative ? -ans : ans;
    }
}
