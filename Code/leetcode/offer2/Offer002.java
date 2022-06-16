package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 002. 二进制加法
 * 简单题：字符串数字相加减
 * @date 4:31 PM 2022/6/12
 */
public class Offer002 {

    public String addBinary(String a, String b) {
        if (a == null || b == null) {
            return a == null ? b : a;
        }
        StringBuilder sb = new StringBuilder();
        int n = a.length() - 1, m = b.length() - 1, carry = 0;
        while (n >= 0 || m >= 0 || carry != 0) {
            int x = n >= 0 ? a.charAt(n--) - '0' : 0;
            int y = m >= 0 ? b.charAt(m--) - '0' : 0;
            carry = x + y + carry;
            sb.append(carry % 2);
            carry /= 2;
        }
        return sb.reverse().toString();
    }

}
