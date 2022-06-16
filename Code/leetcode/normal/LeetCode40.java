package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * 快速选择模板
 *
 * @Author todd
 * @Date 2020/4/4
 */
public class LeetCode40 {

    public int[] getLeastNumbers(int[] arr, int k) {
        if (arr.length == 0 || arr == null || k == 0) {
            return new int[0];
        }
        return quickSort(arr, 0, arr.length - 1, k - 1);
    }

    public int[] quickSort(int[] arr, int left, int right, int k) {
        int i = partition(arr, left, right);
        if (i == k) {
            return Arrays.copyOf(arr, i + 1);
        }
        return i > k ? quickSort(arr, left, i - 1, k) : quickSort(arr, i + 1, right, k);
    }

    public int partition(int[] arr, int left, int right) {
        swap(arr, left + (int) Math.random() * (right - left + 1), right);
        int i, j;
        for (i = left, j = left; j < right; j++) {
            if (arr[j] < arr[right]) {
                swap(arr, i++, j);
            }
        }
        swap(arr, i, right);
        return i;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
