package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 48. 旋转图像 (*)
 * 这题比较巧妙，可以总结归纳下
 * 1. 方法1： 自外向内一共有不超过 n / 2 层（单个中心元素不算一层）矩形框。对于第 times 层矩形框，其框边长 len = nums - (times * 2)，
 * 将其顺时针分为 4 份 len - 1 的边，对四条边进行元素的循环交换即可。
 * 2. 方法2： 先沿右上 - 左下的对角线翻转（270° + 一次镜像），再沿水平中线上下翻转（-180° + 一次镜像），可以实现顺时针 90 度的旋转效果
 * @date 11:50 PM 2022/5/21
 */
public class LeetCode48 {

    public void rotate(int[][] matrix) {
        if(matrix.length == 0 || matrix.length != matrix[0].length) {
            return;
        }
        int nums = matrix.length;
        int times = 0;
        while(times <= (nums >> 1)){
            int len = nums - (times << 1);
            for(int i = 0; i < len - 1; ++i){
                int temp = matrix[times][times + i];
                matrix[times][times + i] = matrix[times + len - i - 1][times];
                matrix[times + len - i - 1][times] = matrix[times + len - 1][times + len - i - 1];
                matrix[times + len - 1][times + len - i - 1] = matrix[times + i][times + len - 1];
                matrix[times + i][times + len - 1] = temp;
            }
            ++times;
        }
    }

    public void rotateOptimized(int[][] matrix) {
        if(matrix.length == 0 || matrix.length != matrix[0].length) {
            return;
        }
        int nums = matrix.length;
        for (int i = 0; i < nums; ++i){
            for (int j = 0; j < nums - i; ++j){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[nums - 1 - j][nums - 1 - i];
                matrix[nums - 1 - j][nums - 1 - i] = temp;
            }
        }
        for (int i = 0; i < (nums >> 1); ++i){
            for (int j = 0; j < nums; ++j){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[nums - 1 - i][j];
                matrix[nums - 1 - i][j] = temp;
            }
        }
    }
}
