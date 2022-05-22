package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 34. 在排序数组中查找元素的第一个和最后一个位置 (*)
 * 整体思路：
 * 1. 二分查询
 * 2. 分两次查询，第一次查找元素的第一个位置，第二次查询最后一个位置，返回结果
 * 3. 这道题要特别考虑边界场景，需要仔细体会
 * @date 6:53 PM 2022/5/21
 */
public class LeetCode34 {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int startIndex = searchStartIndex(nums, target);
        int endIndex = searchEndIndex(nums, target);
        return new int[]{startIndex, endIndex};
    }

    private int searchStartIndex(int[] nums, int target) {
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
        return left < nums.length && nums[left] == target ? left : -1;
    }

    private int searchEndIndex(int[] nums, int target) {
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
        return right >= 0 && nums[right] == target ? right : -1;
    }
}
