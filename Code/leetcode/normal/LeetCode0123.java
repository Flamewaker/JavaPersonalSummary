package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/5/18 15:07
 * @description: 买卖股票的最佳时机 III
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔交易。
 */
public class LeetCode0123 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int[][][] dp = new int[prices.length][2 + 1][2];
        //k需要减的情况是，卖出了股票
        for (int i = 0; i < prices.length; i++) {
            for (int k = 1; k <= 2; k++) {
                if (i == 0) {
                    //第i天，还有k次，手里没有股票，当i=0，手里没股票，最大利润为0
                    dp[i][k][0] = 0;
                    //第i天，手里有股票，因为还没有盈利，最大利润为 负prices[i]
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }

        }
        return dp[prices.length - 1][2][0];

    }
}
