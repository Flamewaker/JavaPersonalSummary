package com.todd.leetcode.offer1;

/**
 * 实现函数double Power(double base, int exponent)，
 * 求base的exponent次方。快速幂模板。注意条件。x = 0, n <= 0。
 * 将exponent化为二进制数
 *
 * @Author todd
 * @Date 2020/4/15
 */
public class Offer16 {
    public double myPow1(double x, int n) {
        if (x == 0) {
            return 0;
        }
        long temp = n;
        double ans = 1.0;
        if (temp < 0) {
            x = 1 / x;
            temp = -temp;
        }
        while (temp > 0) {
            if ((temp & 1) == 1) {
                ans *= x;
            }
            x *= x;
            temp >>= 1;
        }
        return ans;
    }



    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            return 1 / myPow2(x, -N);
        }
        return myPow2(x, N);
    }

    public double myPow2(double x, long n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        if (x == 1) {
            return 1;
        }
        double res = myPow2(x, n >> 1);
        res *= res;
        if ((n & 1) == 1) {
            res *= x;
        }
        return res;
    }
}
