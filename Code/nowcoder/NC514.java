package com.todd.nowcoder;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/8/6 11:31
 * @description: 牛妹打怪兽
 * 区间dp
 */
public class NC514 {
    public int attackMonster(int monsterLength, int[] monsterPoint) {
        // write code here
        Arrays.sort(monsterPoint);
        int n = monsterPoint.length;
        int[][] dp = new int[n + 2][n + 2];

        int[] len = new int[n + 2];
        for (int i = 1; i < n + 1; i++) {
            len[i] = monsterPoint[i - 1];
        }
        len[0] = 0;
        len[n + 1] = monsterLength;
        for (int i = 2; i <= n + 1; i++) {
            for (int j = 0; j <= n + 1 - i; j++) {
                int initialMin = Integer.MAX_VALUE;
                for (int k = j + 1; k < i + j; k++) {
                    int tempV = dp[j][k] + dp[k][j + i] + len[j + i] - len[j];
                    if (initialMin > tempV) {
                        initialMin = tempV;
                    }
                }
                dp[j][j + i] = initialMin;
            }
        }
        return dp[0][n + 1];
    }
}
