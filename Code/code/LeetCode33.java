package com.todd.code;

/**
 * @author todd
 * @date 2020/6/27 16:41
 * @description: 搜索旋转排序数组 LeetCode 33
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 *
 * 利用二分查找进行搜索，每次只能在单调区间中进行精确。
 * LeetCode 81 如果是做的有重复数字的题，nums[left] == nums[mid] => left++; continue;
 */
public class LeetCode33 {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (target < nums[mid] && target >= nums[left]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public boolean search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right){
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[left] == nums[mid]) {
                left++;
                continue;
            }
            if(nums[left] < nums[mid]){
                if(target >= nums[left] && target <= nums[mid]){
                    right = mid;
                }else{
                    left = mid + 1;
                }
            }else{
                if(target >= nums[mid] && target <= nums[right]){
                    left = mid;
                }else{
                    right = mid - 1;
                }
            }
        }
        return false;
    }
}
