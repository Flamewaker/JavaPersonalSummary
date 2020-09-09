package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/7 12:27
 * @description: 34. 在排序数组中查找元素的第一个和最后一个位置
 */
public class LeetCode34 {

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        return new int[]{binarySearchFirstPlace(nums, target), binarySearchLastPlace(nums, target)};
    }

    public int binarySearchFirstPlace(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target || nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        return left < nums.length && nums[left] == target ? left : -1;
    }

    public int binarySearchLastPlace(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target || nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        return right >= 0 && nums[right] == target ? right : -1;
    }

}
