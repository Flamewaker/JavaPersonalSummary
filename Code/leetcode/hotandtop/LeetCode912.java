package com.todd.leetcode.hotandtop;

import java.util.Random;

/**
 * @author tongchengdong
 * @description 912. 排序数组
 * 整体思路：
 * 1. 快排
 * 2. 归并
 * @date 5:49 PM 2022/6/7
 */
public class LeetCode912 {

    Random rand = new Random();

    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        quickSort(nums,0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int index = partition(nums, left, right);
        quickSort(nums, left, index - 1);
        quickSort(nums, index + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int index = left + rand.nextInt(right - left + 1);
        swap(nums, index, left);
        int temp = nums[left];
        int l = left, r = right;
        while (l < r) {
            while (l < r && nums[r] >= temp) {
                r--;
            }
            if (l < r) {
                nums[l] = nums[r];
            }
            while (l < r && nums[l] < temp) {
                l++;
            }
            if (l < r) {
                nums[r] = nums[l];
            }
        }
        nums[l] = temp;
        return l;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        mergeSortInOrder(nums, left, mid, right);
    }

    private void mergeSortInOrder(int[] nums, int left, int mid, int right) {
        int len = right - left + 1;
        int[] arr = new int[len];
        int index = 0;
        int l = left, r = mid + 1;
        while (l <= mid && r <= right) {
            if (nums[l] < nums[r]) {
                arr[index++] = nums[l++];
            } else {
                arr[index++] = nums[r++];
            }
        }
        while (l <= mid) {
            arr[index++] = nums[l++];
        }
        while (r <= right) {
            arr[index++] = nums[r++];
        }
        for (int i = 0, j = left; i < len; i++, j++) {
            nums[j++]= arr[i];
        }
    }
}
