package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/3 20:14
 * @description: 给定一个整数，编写一个函数来判断它是否是 2 的幂次方。
 * 1. 数学方法。
 * 2. 位运算，n & (n - 1)
 * * 恒有 n & (n - 1) == 0，这是因为 n−1 二进制最高位为 0，其余所有位为 1;
 * * 并且有 n > 0;
 */
public class LeetCode231 {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public boolean isPowerOfTwo2(int n) {
        if (n <= 0) {
            return false;
        }
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

}
