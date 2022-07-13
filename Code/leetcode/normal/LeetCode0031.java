package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/6/15 9:17
 * @description: 下一个排列
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * 必须原地修改，只允许使用额外常数空间。
 * 1. 下一个数比当前数大 2. 下一个数比当前数增加的幅度尽量小。
 * 1. 尽可能靠右的低位进行交换。2. 将一个尽可能小的大数与前面的小数交换。3. 大树后面的大数后面的所有数重置为升序，升序排列就是最小的排列。
 */
public class LeetCode0031 {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int len = nums.length;
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                int index = i + 1;
                for (int j = i + 1; j < len; j++) {
                    if (nums[index] > nums[j] && nums[j] > nums[i]) {
                        index = j;
                    }
                }
                System.out.println(i + " " + index);
                swap(nums, i, index);
                Arrays.sort(nums, i + 1, len);
                return;
            }
        }
        reverse(nums, 0, len - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
