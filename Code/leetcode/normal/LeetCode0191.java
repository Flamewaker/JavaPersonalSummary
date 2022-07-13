package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/16 16:31
 * @description: 编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。
 * 舍弃最低位，继续判断下一位（会有负数情况，因此应该使用无符号右移）
 */
public class LeetCode0191 {
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
