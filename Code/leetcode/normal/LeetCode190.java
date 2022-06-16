package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/9 16:08
 * @description: 190. 颠倒二进制位
 */
public class LeetCode190 {
    public int reverseBits(int n) {
        int ans = 0;
        int times = 32;
        while (times-- > 0) {
            ans <<= 1;
            ans += n & 1;
            n >>>= 1;
        }
        return ans;
    }
}
