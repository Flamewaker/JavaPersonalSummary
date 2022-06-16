package com.todd.leetcode.offer2;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 008. 和大于等于 target 的最短子数组
 * 整体思路： 滑动窗口
 * @date 11:01 PM 2022/6/13
 */
public class Offer008 {
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int left = 0, right = 0;
        int sum = 0;
        while (right < n) {
            sum += nums[right];
            while (left <= right && sum >= target) {
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left++];
            }
            right++;
        }
        return ans != Integer.MAX_VALUE ? ans : 0;
    }
}
