package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 11:55
 * @description: 剑指 Offer 53 - I. 在排序数组中查找数字 I
 */
public class Offer53_1 {
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int left = binarySearchTargetStart(nums, target);
        int right = binarySearchTargetEnd(nums, target);
        return left == -1 ? 0 : right - left + 1;
    }

    public int binarySearchTargetStart(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left >= nums.length || nums[left] != target ? -1 : left;
    }

    public int binarySearchTargetEnd(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right < 0 || nums[right] != target ? -1 : right;
    }
}
