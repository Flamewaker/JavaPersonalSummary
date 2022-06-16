package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/9/3 16:07
 * @description: 碰碰数
 * 小Q的老板给了小Q一个长度为n的不重复数列，还有m对碰碰数，希望小Q找出不包含有任一对碰碰数的子区间有多少个？
 * 感觉超时了...-_-!!!!
 */
public class Tencent_Interview_01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] nums = new int[n];
        int[][] pivotsNum = new int[n][2];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        for (int i = 0; i < m; i++) {
            pivotsNum[i][0] = sc.nextInt();
            pivotsNum[i][1] = sc.nextInt();
        }

        boolean[][] dp = new boolean[n][n];
        int count = 0;

        count += n;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j + i < n; j++) {
                boolean flag = false;
                if (dp[j][j + i - 1] || dp[j + 1][j + i]) {
                    dp[j][j + i] = true;
                    continue;
                }

                for (int k = 0; k < m; k++) {
                    if (nums[j] == pivotsNum[k][0] && nums[j + i] == pivotsNum[k][1] || nums[j + i] == pivotsNum[k][0] && nums[j] == pivotsNum[k][1]) {
                        dp[j][j + i] = true;
                        flag = true;
                        continue;
                    }
                }

                if (!flag) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
