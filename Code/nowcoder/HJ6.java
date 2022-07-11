package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ6 质数因子
 * @date 8:51 PM 2022/7/2
 */
public class HJ6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long num = scanner.nextLong();
        long k = (long) Math.sqrt(num);

        for (long i = 2; i <= k; ++i) {
            while (num % i == 0) {
                System.out.print(i + " ");
                num /= i;
            }
        }
        System.out.println(num == 1 ? "" : num + " ");
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        long num = scanner.nextLong();
//
//        for (long i = 2; i <= num; ++i) {
//            while (num % i == 0) {
//                System.out.print(i + " ");
//                num /= i;
//            }
//        }
//        System.out.println();
//    }
}
