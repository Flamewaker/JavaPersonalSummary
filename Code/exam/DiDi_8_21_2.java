package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/21 20:01
 * @description: 斐波拉契数列 + 旋转矩阵
 */
public class DiDi_8_21_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 1) {
            System.out.println(1);
        } else if (n >= 1 && n <= 10) {
            long[] dp = new long[n * n];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i < n * n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            int i = 0, j = 0;
            int count = n * n - 1;
            long[][] matrix = new long[n][n];
            boolean[][] visited = new boolean[n][n];
            int state = 0;
            while (count >= 0) {
                visited[i][j] = true;
                matrix[i][j] = dp[count];
                if (state == 0) {
                    if (j + 1 == n || visited[i][j + 1]) {
                        i++;
                        state = 1;
                    } else {
                        j++;
                    }
                } else if (state == 1) {
                    if (i + 1 == n || visited[i + 1][j]) {
                        j--;
                        state = 2;
                    } else {
                        i++;
                    }
                } else if (state == 2) {
                    if (j - 1 < 0 || visited[i][j - 1]) {
                        i--;
                        state = 3;
                    } else {
                        j--;
                    }
                } else {
                    if (i - 1 < 0 || visited[i - 1][j]) {
                        j++;
                        state = 0;
                    } else {
                        i--;
                    }
                }
                count--;
            }
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < n; l++) {
                    if (l == n - 1) {
                        System.out.println(matrix[k][l]);
                    } else {
                        System.out.print(matrix[k][l] + " ");
                    }
                }
            }
        }

    }

}
