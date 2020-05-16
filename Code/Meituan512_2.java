package com.todd;

import java.util.Scanner;

/**
 * 给n个数，问这n个数中具有的最大汉明距离是多少。
 *
 * @Author todd
 * @Date 2020/5/12
 */
public class Meituan512_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = scanner.nextInt();
        }

        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int res = judgeNums(num[i], num[j]);
                ans = Math.max(res, ans);
            }
        }

        System.out.println(ans);

    }

    private static int judgeNums(int i, int j) {
        int hanMingCode = i ^ j;
        int count = 0;
        while (hanMingCode != 0) {
            count += (hanMingCode & 1) == 1 ? 1 : 0;
            hanMingCode >>= 1;
        }
        return count;
    }
}
