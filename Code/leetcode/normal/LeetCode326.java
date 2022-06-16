package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/9 17:04
 * @description: 326. 3的幂
 */
public class LeetCode326 {
    public boolean isPowerOfThree(int n) {
        if (n <= 0) {
            return false;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    public boolean isPowerOfThree2(int n) {
        if(n <= 0){
            return false;
        }
        double d = Math.log(n)/Math.log(3);
        long i = Math.round(d);
        return Math.abs(d - i) < 1e-10;
    }
}
