package com.todd.redo.nowcoder;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/8/6 14:11
 * @description: TODO
 * 需要先把数字排个序，因为集合是从小往大扩充的。
 * 假如我们已经用a[1] - a[i]的数表示了1 - sum的数，那么加入加入一个temp，temp需要满足tmp <= sum + 1
 * 如果temp > sum + 1, sum + 1将缺失。
 */
public class NC520 {
    public String Throwdice (int n, int m, int[] a, int[] b) {
        if (solve(n, a) > solve(n, b)) {
            return "Happy";
        } else {
            return "Sad";
        }
    }

    public long solve(int n, int[] a) {
        Arrays.sort(a);
        long sum = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] > sum + 1) {
                break;
            }
            sum += a[i];
        }
        return sum + 1;
    }
}
