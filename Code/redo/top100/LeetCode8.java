package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/6 22:35
 * @description: TODO
 */
public class LeetCode8 {
    public int myAtoi(String str) {
        if (str.length() == 0) {
            return 0;
        }
        char[] num = str.toCharArray();
        int length = str.length();
        int index = 0;
        while (index < length && num[index] == ' ') {
            index++;
        }
        if (index == length) {
            return 0;
        }
        boolean negative = false;
        if (num[index] == '+') {
            index++;
        } else if (num[index] == '-') {
            negative = true;
            index++;
        } else if (!Character.isDigit(num[index])) {
            return 0;
        }
        int ans = 0;
        while (index < length && Character.isDigit(num[index])) {
            int n = num[index] - '0';
            if (ans > (Integer.MAX_VALUE - n) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + n;
            index++;
        }
        return negative ? -ans : ans;
    }
}
