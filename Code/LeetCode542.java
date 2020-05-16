package com.todd;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。两个相邻元素间的距离为 1 。
 * BFS逐层往外搜索。
 *
 * @Author todd
 * @Date 2020/4/15
 */
public class LeetCode542 {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};

    public int[][] updateMatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] ans = new int[row][col];
        Queue<int[]> q = new LinkedList<>();
        int[][] visited = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    q.add(new int[]{i, j});
                    visited[i][j] = 1;
                }
            }
        }
        while (!q.isEmpty()) {
            int[] index = q.poll();
            for (int i = 0; i < 4; i++) {
                int r = index[0] + x[i];
                int c = index[1] + y[i];
                if (r >= 0 && r < row && c >= 0 && c < col && visited[r][c] == 0) {
                    ans[r][c] = ans[index[0]][index[1]] + 1;
                    q.add(new int[]{r, c});
                    visited[r][c] = 1;
                }
            }
        }
        return ans;
    }
}
