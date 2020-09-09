package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/22 17:26
 * @description: 首先输入要输入的整数个数n，然后输入n个整数。输出为n个整数中负数的个数，和所有正整数的平均值，结果保留一位小数。
 */
public class HuaWei2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int negNum = 0;
            double posCount = 0;
            int posNum = 0;
            for (int i = 0; i < n; i++) {
                int num = sc.nextInt();
                if (num < 0) {
                    negNum++;
                } else if (num > 0) {
                    posCount += num;
                    posNum++;
                }
            }
            System.out.print(negNum + " ");
            System.out.printf("%.1f", posCount/posNum);
            System.out.println();
        }
    }
}
