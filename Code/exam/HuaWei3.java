package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/22 18:27
 * @description: 自守数是指一个数的平方的尾数等于该数自身的自然数。
 */
public class HuaWei3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            System.out.println(calcAutomorphicNumbers(n));
        }
    }

    public static int calcAutomorphicNumbers(int n){
        int base = 10;
        int count = 0;
        for (int i = 0; i <= n; i++) {
            if (base == i) {
                base *= 10;
            }
            if ((i * i) % base == i) {
                count++;
            }
        }
        return count;
    }
}
