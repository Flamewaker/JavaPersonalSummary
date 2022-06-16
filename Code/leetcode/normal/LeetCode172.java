package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/9 16:05
 * @description: 172. 阶乘后的零
 */
public class LeetCode172 {
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            count += n / 5;
            n /= 5;
        }
        return count;
    }
}
