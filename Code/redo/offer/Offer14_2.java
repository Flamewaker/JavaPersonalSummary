package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 16:15
 * @description: 剑指 Offer 14- II. 剪绳子 II
 */
public class Offer14_2 {
    public int cuttingRope(int n) {
        if (n <= 3) {
            return n - 1;
        }
        int a = n / 3, b = n % 3, p = 1000000007;
        if (b == 0) {
            return (int)(quickPow(3, a, p));
        } else if (b == 1) {
            return (int)(quickPow(3, a - 1, p) * 4 % p);
        } else {
            return (int)(quickPow(3, a, p) * 2 % p);
        }
    }

    public long quickPow(int n, int m, int p) {
        long ans = 1;
        long temp = n;
        while (m != 0) {
            if ((m & 1) == 1) {
                ans = ans * temp % p;
            }
            temp = temp * temp % p;
            m >>= 1;
        }
        return ans;
    }
}
