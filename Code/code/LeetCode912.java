package com.todd.code;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author todd
 * @date 2020/6/24 11:55
 * @description: 快速排序算法递归和非递归实现
 * 单指针和双指针算法
 * 快排递归和非递归实现
 */
public class LeetCode912 {
    Random rand = new Random();

    public int[] sortArray(int[] nums) {
        Sort(0, nums.length - 1, nums);
        return nums;
    }

    public void Sort(int left, int right, int[] nums) {
        if (left >= right) {
            return;
        }
        int mid = singlePartition(left, right, nums);
        Sort(left, mid - 1, nums);
        Sort(mid + 1, right, nums);
    }


    private int singlePartition(int left, int right, int[] nums) {
        int index = rand.nextInt(right - left + 1) + left;
        swap(nums, index, right);
        int i, j;
        for (i = left, j = left; i < right; i++) {
            if (nums[i] <= nums[right]) {
                swap(nums, i, j++);
            }
        }
        swap(nums, right, j);
        return j;
    }

    private int doublePartition(int left, int right, int[] nums) {
        int index = rand.nextInt(right - left + 1) + left;
        swap(nums, index, left);
        int l = left, r = right, temp = nums[left];
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

    //非递归版本实现
    public void noRecQuickSort(int left, int right, int[] nums) {
        LinkedList<Integer> stack = new LinkedList<>();
        push(stack, left, right);
        while (!stack.isEmpty()) {
            int r = stack.pollLast();
            int l = stack.pollLast();
            if (l >= r) {
                continue;
            }
            int mid = singlePartition(l, r, nums);
            push(stack, l, mid - 1);
            push(stack, mid + 1, r);
        }

    }

    private void push(LinkedList<Integer> stack, int left, int right) {
        stack.offerLast(left);
        stack.offerLast(right);
    }

}

