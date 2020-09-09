package com.todd.redo.Star;

import java.util.Arrays;
import java.util.Random;

/**
 * @author todd
 * @date 2020/9/9 14:39
 * @description: 排序算法的实现
 */
public class Sort {

    public static void main(String[] args) {
        int[] nums = new int[] {4,7,9,1,3,3,3,2,2,2,2,2,2,2,3,5};
        quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    static Random random = new Random();

    public static void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int index = doublePointPartition(nums, left, right);
        quickSort(nums, left, index - 1);
        quickSort(nums, index + 1, right);
    }

    public static int doublePointPartition(int[] nums, int left, int right) {
        int index = left + random.nextInt(right - left + 1);
        swap(nums, index, right);
        int i = left, j = right;
        while (i < j) {
            while (i < j && nums[i] < nums[right]) {
                i++;
            }
            while (i < j && nums[j] >= nums[right]) {
                j--;
            }
            if (i < j) {
                swap(nums, i, j);
            }
        }
        swap(nums, i, right);
        return i;
    }

    public static int partition(int[] nums, int left, int right) {
        int index = left + random.nextInt(right - left + 1);
        swap(nums, index, right);

        int i, j;
        for (i = left, j = left; j < right; j++) {
            if (nums[j] < nums[right]) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, right, i);
        return i;
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
