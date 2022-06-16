package com.todd.leetcode.offer1;

/**
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m] 。
 * 请问 k[0]*k[1]*...*k[m] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * 此题与上一题的区别在于此题n的范围变大，可能会造成大数越界的情况，用寻常的dp很可能会超时或出错，故使用贪心思想解决，根据这道题我们可以知道取3越多数值越大，因此直接用贪心算法解决。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer14_2 {
    public int cuttingRope(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        long res = 1;
        while (n > 4) {
            res *= 3;
            res = res % 1000000007;
            n -= 3;
        }
        return (int) (res * n % 1000000007);
    }

    private int mod = (int)1e9 + 7;

    public int cuttingRope2(int n) {
        if(n < 4){
            return n-1;
        }
        int cnt3 = n / 3;
        if(n % 3 == 0){
            return (int)pow(3, cnt3);
        } else if(n % 3 == 1){
            return (int)((pow(3, cnt3 - 1) * 4) % mod);
        } else {
            return (int)((pow(3, cnt3) * 2) % mod);
        }
    }

    private long pow(long base, int num){
        long res = 1;
        while(num > 0){
            if((num & 1) == 1){
                res *= base;
                res %= mod;
            }
            base *= base;
            base %= mod;
            num >>= 1;
        }
        return res;
    }
}
