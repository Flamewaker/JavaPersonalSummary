package com.todd.leetcode.Sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author todd
 * @date 2020/7/5 14:35
 * @description: 自己实现排序算法
 */
public class Sort2 {
    Random random = new Random();
    public void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = partition(nums, left, right);
        quickSort(nums, left, mid - 1);
        quickSort(nums, mid + 1, right);
    }

    public int partition(int[] nums, int left, int right) {
        int index = left + random.nextInt(right - left + 1);
        swap(nums, right, index);
        int i, j;
        for (i = left, j = left; i < right; i++) {
            if (nums[i] < nums[right]) {
                swap(nums, j, i);
                j++;
            }
        }
        swap(nums, j, right);
        return j;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void quickSort2(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        stack.offerLast(0);
        stack.offerLast(nums.length - 1);
        while (!stack.isEmpty()) {
            int right = stack.pollLast();
            int left = stack.pollLast();
            if (left >= right) {
                continue;
            }
            int mid = partition(nums, left, right);
            stack.offer(left);
            stack.offer(mid - 1);
            stack.offer(mid + 1);
            stack.offer(right);
        }
    }

    public void heapSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        int length = nums.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            adjustHeap(nums, i, length);
        }
        for (int i = length - 1; i >= 1; i--) {
            swap(nums, i, 0);
            adjustHeap2(nums, 0, i);
        }
    }

    public void adjustHeap(int[] nums, int index, int length) {
        int temp = nums[index];
        for (int k = 2 * index + 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && nums[k] < nums[k + 1]) {
                k++;
            }
            if (temp < nums[k]) {
                nums[index] = nums[k];
                index = k;
            } else {
                break;
            }
        }
        nums[index] = temp;
    }

    public void adjustHeap2(int[] nums, int index, int length) {
        int n = index;
        for (int k = 2 * n + 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && nums[k] < nums[k + 1]) {
                k++;
            }
            if (nums[k] > nums[n]) {
                swap(nums, k, n);
                n = k;
            } else {
                break;
            }
        }
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        mergeSortInOrder(nums, left, mid, right);
    }

    public void mergeSortInOrder(int[] nums, int left, int mid, int right) {
        int i = left, j = mid + 1;
        int[] temp = new int[right - left + 1];
        int k = 0;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        while (j <= right) {
            temp[k++] = nums[j++];
        }

        for (int z = 0; z < temp.length; z++) {
            nums[left + z] = temp[z];
        }
    }

    public static void main(String[] args) {
        Sort2 sort = new Sort2();
        int[] nums = new int[]{4, 3, 2, 1, 7, 0 ,8, 6, 6, 9, 100, 99};
        sort.mergeSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }
}
