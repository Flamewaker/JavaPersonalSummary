package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 009. 乘积小于 K 的子数组
 * 整体思路：
 * 二分查找
 * @date 11:28 AM 2022/6/14
 */
public class Offer009 {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int len = nums.length;
        int ans = 0;
        int sum = 1;
        int left = 0, right = 0;
        //1. 用滑动窗口，数组长度就是新增的子数组的个数
        while (right < len) {
            sum = sum * nums[right];
            while (sum >= k && left <= right) {
                sum /= nums[left++];
            }
            ans = ans + right - left + 1;
            right++;
        }
        return ans;
    }
}
