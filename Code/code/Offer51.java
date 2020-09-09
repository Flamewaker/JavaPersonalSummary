package com.todd.code;

/**
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
 * （经典）分治思想 + 归并排序 + 求逆序对
 *
 * @Author todd
 * @Date 2020/4/24
 */
public class Offer51 {
    public int reversePairs(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return 0;
        }
        int[] temp = new int[len];
        for (int i = 0; i < len; i++) {
            temp[i] = nums[i];
        }
        return reversePair(temp, 0, len - 1);
    }

    private int reversePair(int[] temp, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        int leftAns = reversePair(temp, left, mid);
        int rightAns = reversePair(temp, mid + 1, right);

        if (temp[mid + 1] >= temp[mid]) {
            return leftAns + rightAns;
        }

        int midAns = mergePair(temp, left, mid, right);

        return leftAns + rightAns + midAns;
    }

    private int mergePair(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        for (int i = 0; i <= right - left; i++) {
            temp[i] = nums[i + left];
        }
        int i = left;
        int j = mid + 1;
        int ans = 0;
        for (int k = 0; k <= right - left; k++) {
            if (i == mid + 1) {
                nums[k + left] = temp[j - left];
                j++;
            } else if (j == right + 1) {
                nums[k + left] = temp[i - left];
                i++;
            } else if (temp[i - left] <= temp[j - left]) {
                nums[k + left] = temp[i - left];
                i++;
            } else {
                nums[k + left] = temp[j - left];
                j++;
                ans += mid + 1 - i;
            }
        }
        return ans;
    }
}
