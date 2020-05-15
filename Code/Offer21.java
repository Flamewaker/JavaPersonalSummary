package com.todd;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，
 * 双指针遍历，类似快排。
 * 所有偶数位于数组的后半部分。
 *
 * @Auther todd
 * @Date 2020/4/16
 */
public class Offer21 {
    public int[] exchange(int[] nums) {
        if (nums == null) {
            return null;
        }
        int len = nums.length;
        int i = 0;
        int j = len - 1;
        while (i < j) {
            while (i < j && nums[i] % 2 == 1) {
                i++;
            }
            while (i < j && nums[j] % 2 == 0) {
                j--;
            }
            if (i < j) {
                swap(i, j, nums);
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
