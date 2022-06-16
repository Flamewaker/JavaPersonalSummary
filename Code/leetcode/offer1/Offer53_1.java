package com.todd.leetcode.offer1;

/**
 * @Author todd
 * @Date 2020/5/12
 */
public class Offer53_1 {
    public int search(int[] nums, int target) {
        int len = nums.length;
        int left = searchLowerBound(nums, target, 0, len - 1);
        int right = searchUpperBound(nums, target, 0, len - 1);
        return left > -1 && right > -1 ? right - left + 1 : 0;
    }

    public int searchLowerBound(int[] nums, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        if (nums[mid] == target && ((mid == 0) || (nums[mid] > nums[mid - 1]))) {
            return mid;
        }

        if (nums[mid] >= target) {
            return searchLowerBound(nums, target, low, mid - 1);
        } else {
            return searchLowerBound(nums, target, mid + 1, high);
        }

    }

    public int searchUpperBound(int[] nums, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        if (nums[mid] == target && ((mid == nums.length - 1) || (nums[mid] < nums[mid + 1]))) {
            return mid;
        }

        if (nums[mid] <= target) {
            return searchUpperBound(nums, target, mid + 1, high);
        } else {
            return searchUpperBound(nums, target, low, mid - 1);
        }
    }
}
