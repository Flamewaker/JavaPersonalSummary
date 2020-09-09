package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 14:31
 * @description: 数列的和
 * 数列的定义如下：数列的第一项为n，以后各项为前一项的平方根，求数列的前m项的和。
 * 简单题。
 */
public class ByteDance07 {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {

            double N = in.nextDouble();
            int M = in.nextInt();
            double result = 0;

            for (int i = 0; i < M; i++) {
                result += N;
                N = Math.sqrt(N);
            }

            System.out.printf("%.2f\n", result);
        }

    }
}
