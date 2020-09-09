package com.todd.code;

import java.util.HashSet;

/**
 * @author todd
 * @date 2020/6/16 9:01
 * @description: TODO
 * 1. 第一反应是用hash表()，没有思考细节，只是觉得hash表肯定是可以搞定的，但是空间复杂度是 O(n)
 * 2. 按位操作符的字面意思很好理解，即对值的二进制格式进行处理的操作符。而异或的作用为：假设有值甲、乙，当甲乙值相等时，异或操作后结果为不等（False，0），反之，为相等（True，1）。所以按位异或操作符的释义便显而易见了：对某值的每个位上的值（0或1）进行异或操作。
 */
public class LeetCode136 {
    public int singleNumber(int[] nums) {
        int ans = 0;
        for(int i = 0; i < nums.length; i++) {
            ans ^= nums[i];
        }
        return ans;
    }
}
