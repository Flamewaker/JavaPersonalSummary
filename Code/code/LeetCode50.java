package com.todd.code;

/**
 * @author todd
 * @date 2020/6/15 13:23
 * @description: 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 * 快速幂法 可将时间复杂度降低至 O(logn)， 任何一个十进制都能够转换成为一个二进制数，快速幂法就是利用二分法对求幂运算进行加速。
 * 也就是任何一个幂指数当做二进制展开，最后利用展开的形式，可以对已经计算的结果进行保存，而不必重新计算。
 * 唯一需要注意的是，对于n=-2^31，直接取相反数会溢出。
 */
public class LeetCode50 {
    public double myPow(double x, int n) {
        if (x == 0) {
            return 0;
        }
        //第一个关键点
        long temp = n;
        double ans = 1.0;
        //第二个关键点
        if (temp < 0) {
            x = 1 / x;
            temp = -temp;
        }
        while (temp > 0) {
            //第三个关键点
            if ((temp & 1) == 1) {
                ans *= x;
            }
            x *= x;
            temp >>= 1;
        }
        return ans;
    }
}
