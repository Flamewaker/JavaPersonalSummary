package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/3 20:36
 * @description: 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 一个慢（index）指针，一个快（i）指针；当快指针指向的值不等于0时，将其与慢指针（此时的慢指针指向待交换的位置）进行交换，并使慢指针往后挪一位，指向新的代交换位置。
 */
public class LeetCode0283 {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int count = 0;
        int i = 0;
        int len = nums.length;
        while (i < len) {
            if (nums[i] == 0) {
                count++;
            } else {
                nums[i - count] = nums[i];
            }
            i++;
        }
        for (int j = len - count; j < len; j++) {
            nums[j] = 0;
        }
    }

    public void moveZeroes2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int index = 0;
        int i = 0;
        int len = nums.length;
        while (i < len) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
            i++;
        }
        for (int j = index; j < len; j++) {
            nums[j] = 0;
        }
    }


}

