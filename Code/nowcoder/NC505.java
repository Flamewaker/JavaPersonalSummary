package com.todd.nowcoder;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/8/5 16:16
 * @description: 一样的水
 * 有n个水桶，第i个水桶里面水的体积为Ai，你可以用1秒时间向一个桶里添加1体积的水。
 * 有q次询问，每次询问一个整数pi,你需要求出使其中pi个桶中水的体积相同所花费的最少时间。
 * 对于一次询问如果有多种方案，则采用使最终pi个桶中水的体积最小的方案。
 */
public class NC505 {
    public int[] solve (int n, int q, int[] a, int[] p) {
        Arrays.sort(a);
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int min = Integer.MAX_VALUE, minJ = 0;
            int sum = 0;
            for (int m = 0; m < p[i]; m++) {
                sum += a[m];
            }
            min = a[p[i] - 1] * p[i] - sum;
            for (int j = 1; j <= a.length - p[i]; j++) {
                sum -= a[j - 1];
                sum += a[j + p[i] - 1];
                if (a[j + p[i] - 1] * p[i] - sum < min) {
                    minJ = j;
                    min = a[j + p[i] - 1] * p[i] - sum;
                }
            }
            ans[i] = min;
            for (int l = minJ; l < minJ + p[i] - 1; l++) {
                a[l] = a[minJ + p[i] - 1];
            }
        }
        return ans;
    }
}
