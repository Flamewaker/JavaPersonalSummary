package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/22 16:21
 * @description: 寻找峰值
 * 二分查找
 */
public class LeetCode0162 {
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (mid > 0 && nums[mid] < nums[mid - 1]) {
                right = mid - 1;
            } else if (mid < nums.length - 1 && nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
