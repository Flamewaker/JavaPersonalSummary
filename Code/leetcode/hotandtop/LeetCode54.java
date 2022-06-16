package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 54. 螺旋矩阵
 * @date 4:42 PM 2022/6/7
 */
public class LeetCode54 {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return ans;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int level = 0;

        // 统计矩阵从外向内的层数，如果矩阵非空，那么它的层数至少为1层
        int count = (Math.min(m, n) + 1) / 2;
        while (level < count) {
            for (int i = level; i < n - level; i++) {
                ans.add(matrix[level][i]);
            }
            for (int i = level + 1; i < m - level; i++) {
                ans.add(matrix[i][(n - 1) -level]);
            }
            for (int i = (n - 1) - (level + 1); i >= level && (m - 1) - level != level; i--) {
                ans.add(matrix[(m - 1) - level][i]);
            }
            for (int i = (m - 1) - (level + 1); i > level && (n - 1) - level != level; i--) {
                ans.add(matrix[i][level]);
            }
            level++;
        }
        return ans;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                ans.add(matrix[top][i]);
            }
            top++;
            for (int i = top; i <= bottom; i++) {
                ans.add(matrix[i][right]);
            }
            right--;
            if (top > bottom || left > right) {
                break;
            }
            for (int i = right; i >= left; i--) {
                ans.add(matrix[bottom][i]);
            }
            bottom--;
            for (int i = bottom; i >= top; i--) {
                ans.add(matrix[i][left]);
            }
            left++;
        }
        return ans;
    }
}
