package com.todd.nowcoder;

/**
 * @author todd
 * @date 2020/9/5 11:10
 * @description: 牛牛打怪兽
 * 简单贪心
 */
public class NC564 {
    public int slove (int n, int[] A) {
        if (n <= 2 || (n & 1) == 0) {
            return -1;
        }
        int ans = 0;
        for (int i = n / 2; i >= 1; i--) {
            int count = Math.max(A[i * 2 - 1], A[i * 2]);
            A[i - 1] = Math.max(0, A[i - 1] - count);
            ans += count;
        }
        ans += A[0];
        return ans;
    }
}
