package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 19:33
 * @description: 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
 */
public class Offer21 {
    public int[] exchange(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            while (left < right && nums[right] % 2 == 0) {
                right--;
            }
            while (left < right && nums[left] % 2 == 1) {
                left++;
            }
            if (left < right) {
                swap(left, right, nums);
            }
        }
        return nums;
    }

    public void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
