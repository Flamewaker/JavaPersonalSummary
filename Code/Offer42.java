package com.todd;

/**
 * 输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer42 {
    public int maxSubArray(int[] nums) {
        if (nums == null) {
            return 0;
        }
        // 全局最大值
        int max = nums[0];
        // 前一个子组合的最大值,状态压缩
        int subMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (subMax > 0) {
                // 前一个子组合最大值大于0，正增益
                subMax = subMax + nums[i];
            } else {
                // 前一个子组合最大值小于0，抛弃前面的结果
                subMax = nums[i];
            }
            // 计算全局最大值
            max = Math.max(max, subMax);
        }
        return max;
    }
}
