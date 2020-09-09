package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/3 17:28
 * @description: 输出在数组中第一个大于等于查找值的位置，如果数组中不存在这样的数，则输出数组长度加一。
 */
public class NC105 {
    public int upper_bound_ (int n, int v, int[] a) {
        if (n == 0) {
            return n + 1;
        }
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (a[mid] >= v) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left < n ? left + 1 : n + 1;
    }
}
