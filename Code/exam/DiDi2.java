package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/28 15:02
 * @description: 末尾为0的个数
 * 输入一个正整数n,求n!(即阶乘)末尾有多少个0？ 比如: n = 10; n! = 3628800,所以答案为2
 */
public class DiDi2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n  = sc.nextInt();
        System.out.println(findZeroNum(n));
    }

    public static int findZeroNum(int n) {
        int temp = n;
        int count = 0;
        while (temp != 0) {
            count += temp / 5;
            temp /= 5;
        }
        return count;
    }
}
