package com.todd.code;

/**
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer4 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int i = matrix.length - 1, j = 0;
        while(i >= 0 && j < matrix[0].length){
            if(matrix[i][j] > target) {
                i--;
            }
            else if(matrix[i][j] < target) {
                j++;
            }
            else {
                return true;
            }
        }
        return false;

    }
}
