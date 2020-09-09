package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 10:00
 * @description: 剑指 Offer 42. 连续子数组的最大和
 */
public class Offer42 {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cur < 0) {
                cur = 0;
            }
            cur += nums[i];
            ans = Math.max(ans, cur);
        }
        return ans;
    }
}
