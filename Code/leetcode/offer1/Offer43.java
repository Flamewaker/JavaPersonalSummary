package com.todd.leetcode.offer1;

/**
 * @author todd
 * @date 2020/7/12 7:46
 * @description: 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
 * 1. 设当前位为cur。低位为low，高位为high，位因子为digit
 * 2. 当 cur = 0 时： 此位 1 的出现次数只由高位决定，计算公式为：high × digit
 * 3. 当 cur = 1 时： 此位 1 的出现次数由高位 high 和低位 low 决定，计算公式为：high × digit + low+1
 * 4. 当 cur = 2, 3, ..., 9 时： 此位 1 的出现次数只由高位 high 决定，计算公式为：(high+1) × digit
 * 求高位以及不断乘10取当前位的i需要用long表示，因为取一些很大的int的高位就溢出了
 */
public class Offer43 {
    public int countDigitOne(int n) {
        int count = 0;
        long digit = 1;
        long cur = 0;
        long low = 0;
        long high = 0;
        while ((n / digit) != 0) {
            cur = (n / digit) % 10;
            high = n / (digit * 10);
            low = n - (n / digit) * digit;
            if (cur == 1) {
                count += high * digit + low + 1;
            } else if (cur == 0) {
                count += high * digit;
            } else {
                count += (high + 1) * digit;
            }
            digit *= 10;
        }
        return count;
    }
}
