package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 10:05
 * @description: 剑指 Offer 43. 1～n整数中1出现的次数
 */
public class Offer43 {
    public int countDigitOne(int n) {
        int count = 0;
        int digit = 1;
        int cur = 0;
        int high = 0;
        int low = 0;
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
