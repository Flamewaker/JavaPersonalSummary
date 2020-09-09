package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/31 22:36
 * @description: 倒水
 * 要把m升的水倒入n个相同的容器中（假设容器足够大），允许有的容量是空的，问共有多少种不同的倒法？（用k表示）5，1，1和1，5，1和1，1，5是同一种倒法。
 *
 * 问题可转化为将m个相同物体放入n个相同的容器中有多少种放法
 */
public class Enterprise02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        for (int i = 0; i < x; i++) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            System.out.println(solve2(m, n));
        }
    }


    /**
     * f(m,n)为把m升水倒入n个容器的总的方法数。
     * f(m,n)可以分为两种情况，一种是n个容器内都一定有水，一种是n个容器内至少有一个没有水，这两种情况完全独立。
     * 对于第一种，我们先将m中分出n升水，依次倒入每个桶，这样每个桶都一定有水，那么接下来的水的分配就是f（m-n,n）。对于第二种的想法，就是先丢掉一个桶，这个桶一定没水，然后将m升水分配给余下的n-1个桶里（每个桶也不一定有水）。所以有
     * dp[i][j] = dp[i][j-1] + dp[i-j][j] 。以上的讨论都是基于m>=n的情况。
     * 下面是m < n的情况，也就是水少桶多，那么桶太多必定有部分桶未装水的情况。dp[m][n] = dp[m][m]；
     * 接下来初始化边值条件：dp[0][i]=1, dp[j][1] = 1。
     * @param m
     * @param n
     * @return
     */
    public static int solve(int m, int n) {
        if (m == 0 || n == 1) {
            return 1;
        }
        if (n > m) {
            return solve(m, m);
        }
        else {
            return solve(m, n - 1) + solve(m - n, n);
        }
    }

    public static int solve2(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i <= m; i++) {
            dp[i][1] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                if (i < j) {
                    dp[i][j] = dp[i][i];
                } else {
                    dp[i][j] = dp[i - j][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m][n];
    }

}
