package com.todd.leetcode.hotandtop;

import java.util.Arrays;

/**
 * @author tongchengdong
 * @description 322. 零钱兑换
 * 整体思路：
 * 1. dfs搜索 + 剪枝 原来不超时，现在已经超时了
 * 2. 动态规划：dp[j] = min(dp[j - coins[i]] + 1, dp[j]);
 * @date 5:09 PM 2022/6/4
 */
public class LeetCode322 {

    static int ans = Integer.MAX_VALUE;
    public static int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        searchCoins(coins, coins.length - 1, amount, 0);
        return ans == Integer.MAX_VALUE? -1 : ans;
    }

    public static void searchCoins(int[] coins, int n, int amount, int coinNum) {
        if (n < 0 || ans <= coinNum + amount / coins[n]) {
            return;
        }
        if (amount % coins[n] == 0) {
            ans = Math.min(ans, coinNum + amount / coins[n]);
        }
        for (int i = amount / coins[n]; i >= 0; i--) {
            searchCoins(coins, n - 1, amount - i * coins[n], coinNum + i);
        }
    }

    public int coinChangeDP(int[] coins, int amount) {
        if (coins == null || coins.length <= 0) {
            return -1;
        }
        int[] dp = new int[amount + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = amount + 1;
            for (int j = 0; j < coins.length; j++) {
                if (i < coins[j]) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
