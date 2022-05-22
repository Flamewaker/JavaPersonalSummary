package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 75. 颜色分类
 * 1. 第一种思路：第一次遍历将所有的0换到头部。第二次遍历将所有的1换到0之后。思路可以简化，一次遍历将所有的0换到头部，将所有的2换到末尾。
 * 2. 第二种思路：自己实现排序算法。
 * @date 11:41 AM 2022/5/22
 */
public class LeetCode75 {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int n = nums.length;
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                swap(nums, i, index++);
            }
        }
        for (int i = index; i < n; i++) {
            if (nums[i] == 1) {
                swap(nums, i, index++);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void sortColorsSimplified(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while (i <= right) {
            if (nums[i] == 2) {
                swap(nums, i, right--);
            } else if (nums[i] == 0) {
                swap(nums, i++, left++);
            } else {
                i++;
            }
        }
    }
}
