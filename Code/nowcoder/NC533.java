package com.todd.nowcoder;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/9/5 9:34
 * @description: 远亲不如近邻
 * 牛牛最近搬到了一座新的城镇，这个城镇可以看成是一个一维的坐标系。城镇上有n个居民，第i个居民的位置为a_i。现在牛牛有m个搬家方案，在第i个方案中他会搬到位置x_i。
 * 俗话说的好，远亲不如近邻。现在牛牛想知道，对于每个搬家方案，搬家后与最近的居民的距离为多少。
 *
 * 简单二分法
 */
public class NC533 {
    public int[] solve (int n, int m, int[] a, int[] x) {
        if (n <= 0) {
            return new int[0];
        }
        Arrays.sort(a);
        int[] ans = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            int left = 0;
            int right = a.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (a[mid] > x[i]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (right < 0) {
                ans[i] = a[0] - x[i];
            } else if (right == a.length - 1) {
                ans[i] = x[i] - a[right];
            } else {
                ans[i] = Math.min(x[i] - a[right], a[right + 1] - x[i]);
            }
        }
        return ans;

    }


}
