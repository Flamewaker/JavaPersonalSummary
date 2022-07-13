package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/6/3 20:25
 * @description: 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 1. 直接排序
 * 2. 计数：
 * 3. 双指针：冒泡排序思路
 *
 */
public class LeetCode0075 {
    public void sortColors(int[] nums) {
        Arrays.sort(nums);
    }

    public void sortColors2(int[] nums) {
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

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
