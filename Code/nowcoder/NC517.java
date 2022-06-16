package com.todd.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 12:29
 * @description: TODO
 * 对于i位数的答案，是与i-1位和i-2位的答案有关系的
 * 假设a[i]表示i位数中满足条件的数，如果没有连续的6
 * （1）第i位是0,1,2,3,4,5,7,8,9，第i-1位随便
 * （2）第i位是6，第i-1位是0,1,2,3,4,5,7,8,9，第i-2位随便
 * a[i] += (a[i-1] + a[i-2]) * 9;
 */
public class NC517 {
    public String calculate(int n) {
        long dp[] = new long[n + 1];
        dp[1] = 10;
        dp[2] = 99;
        for (int i = 3; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) * 9;
        }

        Long count = dp[n];
        return count.toString();
    }
}
