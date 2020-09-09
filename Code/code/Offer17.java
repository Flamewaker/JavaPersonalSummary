package com.todd.code;

/**
 * 输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。
 * 比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
 * 应该用大数模板
 *
 * @Author todd
 * @Date 2020/4/15
 */
public class Offer17 {
    public static int[] printNumbers(int n) {
        int max = (int) Math.pow(10, n);
        int[] ans = new int[max - 1];
        for (int i = 1; i <= max - 1; i++) {
            ans[i - 1] = i;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(printNumbers(2));
    }
}
