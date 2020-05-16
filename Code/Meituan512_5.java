package com.todd;

import java.util.Scanner;

/**
 * 通过9%
 *
 * @Author todd
 * @Date 2020/5/12
 */
public class Meituan512_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = scanner.nextInt();
        }

        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            ans[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (ans[i] == 1) {
                continue;
            } else {
                for (int j = i + 1; j < n; j++) {
                    if ((num[i] & num[j]) == 0) {
                        ans[i] = 1;
                        ans[j] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.print(ans[i]);
            if (i != n - 1) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
        }

    }
}
