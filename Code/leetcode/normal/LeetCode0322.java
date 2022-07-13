package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/7/3 8:21
 * @description: 322. 零钱兑换
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 动态规划 或 回溯 + 深度剪枝
 */
public class LeetCode0322 {
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

    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        System.out.println(coinChange(coins, amount));
    }

    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for(int i = 0; i < amount + 1; i++){
            dp[i] = i == 0 ? 0 : amount + 1;
            for(int coin : coins){
                if(i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
            }
        }
        if(dp[amount] == amount + 1){
            return -1;
        }else{
            return dp[amount];
        }
    }

    public int coinChange3(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        for (int i = 1; i < amount + 1; i++) {
            dp[i] = amount + 1;
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] < 0) {
                    break;
                }
                dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

}
