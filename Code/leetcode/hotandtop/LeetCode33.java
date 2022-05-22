package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 33. 搜索旋转排序数组
 * 整体思路：
 * 1. 二分查找
 * 2. nums[mid] == target return;
 * 3. nums[left] <= target < nums[mid]  ?  [left, mid) 区间内普通二分查询
 * 4. nums[mid] < target <= nums[right] ?  (mid, right] 区间内普通二分查询
 * 5. 如何缩小到指定区间？ 不满足上述条件，则转移至另一半的空间中进行搜索
 * @date 6:04 PM 2022/5/21
 */
public class LeetCode33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) >> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] <= nums[right]) {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}
