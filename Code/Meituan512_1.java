package com.todd;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 有n个小朋友，每个小朋友m个成绩。对具有最好成绩的小朋友发奖状，每人最多一个奖状。
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Meituan512_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] num = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                num[i][j] = scanner.nextInt();
            }
        }

        int[] maxNums = new int[m];

        HashSet<Integer> ans = new HashSet<>();

        for (int j = 0; j < m; j++) {
            int maxNum = 0;
            for (int i = 0; i < n; i++) {
                maxNum = Math.max(maxNum, num[i][j]);
            }
            maxNums[j] = maxNum;
        }

        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (maxNums[j] == num[i][j]) {
                    ans.add(i);
                }
            }
        }

        System.out.println(ans.size());
    }

}
