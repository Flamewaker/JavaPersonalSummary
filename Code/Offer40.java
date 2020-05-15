package com.todd;

import java.util.Arrays;

/**
 * 输入整数数组 arr ，找出其中最小的 k个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 * 思路：快排算法
 * @Auther todd
 * @Date 2020/5/15
 */
public class Offer40 {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(arr.length == 0 || arr == null || k == 0){
            return new int[0];
        }
        return quickSort(arr, 0, arr.length - 1, k - 1);
    }

    public int[] quickSort(int[] arr, int left, int right, int k){
        int i = partition(arr, left, right);
        if(i == k){
            return Arrays.copyOf(arr, i + 1);
        }
        return i > k? quickSort(arr, left, i - 1, k) : quickSort(arr, i + 1, right, k);
    }

    public int partition(int[] arr, int left, int right){
        swap(arr, left + (int)Math.random() * (right - left + 1),  right);
        int i, j;
        for(i = left, j = left; j < right; j++){
            if(arr[j] < arr[right]){
                swap(arr, i++, j);
            }
        }
        swap(arr, i, right);
        return i;
    }

    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
