package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/23 21:13
 * @description: 找到 l 到 r 之间最少由几个回文串组成
 *
 */
public class Tencent_8_23_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int length = str.length();
        boolean[][] range = new boolean[length + 1][length + 1];
        int[] dp = new int[length + 1];

        for (int i = 1; i <= length; i++) {
            dp[i] = i;
            for (int j = 1; j <= i; j++) {
                if (str.charAt(j - 1) == str.charAt(i - 1) && (i - j < 2 || range[j + 1][i - 1])) {
                    range[j][i] = true;
                    if (j - 1 > 0) {
                        dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }

        }

        System.out.println(dp[length]);
    }
}
