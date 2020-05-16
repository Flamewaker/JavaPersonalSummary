package com.todd;

import java.util.Scanner;

/**
 * @Author todd
 * @Date 2020/4/13
 */
public class Alibaba43_1 {

    static int res = Integer.MAX_VALUE;
    static int cnt = 0;
    static int[][] dp;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        dp = new int[n][m];
        int[][] array = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
        for (int j = 0; j < m; j++) {
            dfs(array, 0, j, 0);
        }
        System.out.println(res);
    }


    public static void dfs(int[][] array, int i, int j, int count) {
        //如果此时同一个值已经被遍历过并且取值更小，则没必要再对它遍历
        if (i < 0 || j < 0 || j >= array[0].length || (i < array.length && dp[i][j] > 0 && array[i][j] + count > dp[i][j])) {
            return;
        }
        if (i == array.length) {
            res = Math.min(res, count);
            return;
        }

        int count1 = count + array[i][j];
        //用来记录遍历到该点所消耗的体力值
        dp[i][j] = count1;
        dfs(array, i + 1, j, count1);
        dfs(array, i, j - 1, count1);
        dfs(array, i, j + 1, count1);
        dfs(array, i - 1, j, count1);
    }
}
