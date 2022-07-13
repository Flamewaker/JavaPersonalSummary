package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/21 12:52
 * @description: 66. 加一
 * 简单题。
 */
public class LeetCode0066 {
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] %= 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        int[] nums = new int[digits.length + 1];
        nums[0] = 1;
        return nums;
    }
}
