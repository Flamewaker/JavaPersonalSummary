package com.todd.leetcode.normal;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/8/9 15:49
 * @description: 166. 分数到小数
 */
public class LeetCode0166 {
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder s = new StringBuilder();
        long n = Math.abs((long) numerator);
        long m = Math.abs((long) denominator);
        if (numerator > 0 && denominator < 0 || numerator < 0 && denominator > 0) {
            s.append("-");
        }
        s.append(n / m);
        long rem = n % m;
        if (rem != 0) {
            HashMap<Long, Integer> map = new HashMap<>();
            s.append(".");
            while (rem > 0) {
                if (map.containsKey(rem)) {
                    s.insert(map.get(rem), "(");
                    s.append(")");
                    break;
                }
                map.put(rem, s.length());
                rem *= 10;
                long res = rem / m;
                rem = rem % m;
                s.append(res);
            }
        }
        return s.toString();
    }
}
