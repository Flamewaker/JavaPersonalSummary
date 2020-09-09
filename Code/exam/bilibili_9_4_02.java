package com.todd.exam;

import java.util.Scanner;

/**
 * 螺旋打印矩阵
 * @author think
 */
public class bilibili_9_4_02 {
    public int[] SpiralMatrix (int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }

        int statue = 0;
        int count = matrix.length * matrix[0].length;
        int m = matrix.length;
        int n = matrix[0].length;
        int[] ans = new int[count];
        boolean[][] visited = new boolean[m][n];
        int i = 0, j = 0;
        int index = 0;
        while (count-- > 0) {
            ans[index] = matrix[i][j];
            visited[i][j] = true;
            if (statue == 0) {
                if (j + 1 == n || visited[i][j + 1]) {
                    i++;
                    statue = 1;
                } else {
                    j++;
                }
            } else if (statue == 1) {
                if (i + 1 == m || visited[i + 1][j]) {
                    j--;
                    statue = 2;
                } else {
                    i++;
                }
            } else if (statue == 2) {
                if (j - 1 < 0 || visited[i][j - 1]) {
                    i--;
                    statue = 3;
                } else {
                    j--;
                }
            } else {
                if (i - 1 < 0 || visited[i - 1][j]) {
                    j++;
                    statue = 0;
                } else {
                    i--;
                }
            }

            index++;
        }
        return ans;
    }
}
