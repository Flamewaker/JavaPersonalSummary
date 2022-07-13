package com.todd.leetcode.normal;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。说明：算法应该具有线性时间复杂度。 不使用额外空间来实现吗.
 * 数组中的全部元素的异或运算结果即为数组中只出现一次的数字。
 * @Author todd
 * @Date 2020/5/14
 */
public class LeetCode0126 {
    public int singleNumber(int[] nums) {
        int ans = 0;
        for(int i = 0; i < nums.length; i++) {
            ans ^= nums[i];
        }
        return ans;
    }
}
