package com.todd.redo.nowcoder;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Random;

/**
 * @author todd
 * @date 2020/9/9 20:46
 * @description: TODO
 */
public class NC88 {
    Random random = new Random();
    public int findKth(int[] a, int n, int K) {
        return quickSelect(a, 0, a.length - 1, n - K + 1);
    }

    public int quickSelect(int[] a, int left, int right, int K) {
        if (left >= right) {
            return a[left];
        }
        int index = partition(a, left, right);
        if (index + 1 == K) {
            return a[index];
        } else if (index + 1 > K) {
            return quickSelect(a, left, index - 1, K);
        } else {
            return quickSelect(a, index + 1, right, K);
        }
    }

    public int partition(int[] a, int left, int right) {
        int index = left + random.nextInt(right - left + 1);
        swap(a, index, right);
        int i = left, j = right;
        while (i < j) {
            while (i < j && a[i] < a[right]) {
                i++;
            }
            while (i < j && a[j] >= a[right]) {
                j--;
            }
            if (i < j) {
                swap(a, i, j);
            }
        }
        swap(a, right, i);
        return i;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
