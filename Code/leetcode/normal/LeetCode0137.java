package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/9 14:22
 * @description: 137. 只出现一次的数字 II
 */
public class LeetCode0137 {
    public int singleNumber(int[] nums) {
        int a = 0, b = 0;
        for (int num : nums) {
            a = (a ^ num) & ~b;
            b = (b ^ num) & ~a;
        }
        return a;
    }
}
