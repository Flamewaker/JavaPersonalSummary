package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 16:55
 * @description: 剑指 Offer 15. 二进制中1的个数
 */
public class Offer15 {
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }
        return count;
    }
}
