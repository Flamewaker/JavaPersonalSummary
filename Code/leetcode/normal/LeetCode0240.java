package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/15 11:14
 * @description: 编写一个高效的算法来搜索mxn矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * 会发现每次都是向左数字会变小，向下数字会变大，有点和二分查找树相似。
 * 如果 target 的值大于当前值，那么就向下走。
 * 如果 target 的值小于当前值，那么就向左走。
 * 如果相等的话，直接返回 true 。
 */
public class LeetCode0240 {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int m = 0;
        int n = matrix[0].length - 1;
        while (m < matrix.length && n >= 0) {
            if (matrix[m][n] == target) {
                return true;
            } else if (matrix[m][n] > target) {
                n--;
            } else {
                m++;
            }
        }
        return false;
    }


    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] > target) {
                break;
            }
            if(matrix[i][matrix[i].length - 1] < target){
                continue;
            }
            int col = binarySearch(matrix[i], target);
            if (col != -1) {
                return true;
            }
        }
        return false;
    }

    //二分查找
    //比如某一行的第一个元素大于了 target ，当前行和后边的所有行都不用考虑了，直接返回 false。
    //某一行的最后一个元素小于了 target ，当前行就不用考虑了，换下一行。

    private int binarySearch(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) >>> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

}
