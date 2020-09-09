package com.todd.code;

/**
 * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
 * 思路：看到排序数组，可以想到双指针法。双指针left, right分别指向数组 nums的左右两端 （俗称对撞双指针）。
 * 计算数值的和，判断指针移动的方向。时间复杂度是O(n), 空间复杂度为O(1);
 * 使用HashMap可以遍历找到需要找到的数字组合，时间复杂度和空间复杂度均为O(n);
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer57_1 {
    public int[] twoSum(int[] nums, int target) {
        if(nums == null || nums.length <= 1) {
            return new int[0];
        }
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            if(nums[left] + nums[right] == target) {
                return new int[]{nums[left], nums[right]};
            } else if(nums[left] + nums[right] > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[0];
    }
}
