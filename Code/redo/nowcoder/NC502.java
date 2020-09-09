package com.todd.redo.nowcoder;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author todd
 * @date 2020/8/5 20:33
 * @description: 数组求和统计
 *
 */
public class NC502 {
    public int countLR (int[] a, int[] b) {
        int lengthA = a.length;
        int ans = 0;
        for (int i = 0; i < lengthA; i++) {
            for (int j = i; j < lengthA; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += a[k];
                }
                if (sum == b[i] + b[j]) {
                    ans ++;
                }

            }
        }
        return ans;
    }

    //S(r) - S(l - 1) = b(l) + b(r)
    //b(l) + S(l - 1) = S(r) - b(r)

    public int countLR2 (int[] a, int[] b) {
        int length = a.length;
        int ans = 0;
        int[] preSum = new int[length + 1];
        for (int i = 1; i <= length; i++) {
            preSum[i] = preSum[i - 1] + a[i - 1];
        }
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < length; i++) {
            cnt.put(b[i] + preSum[i], cnt.getOrDefault(b[i] + preSum[i], 0) + 1);
            ans += cnt.getOrDefault(preSum[i + 1] - b[i], 0);
        }
        return ans;
    }
}
