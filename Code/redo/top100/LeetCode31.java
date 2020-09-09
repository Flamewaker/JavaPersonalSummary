package com.todd.redo.top100;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/8/7 15:16
 * @description: 31. 下一个排列
 */
public class LeetCode31 {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int length = nums.length;
        for (int i = length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                int index = i + 1;
                for (int j = i + 1; j < length; j++) {
                    if (nums[j] > nums[i]) {
                        index = j;
                    }
                }
                swap(nums, i, index);
                Arrays.sort(nums, i + 1, length);
                return;
            }
        }
        reverse(nums, 0, length - 1);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }
}
