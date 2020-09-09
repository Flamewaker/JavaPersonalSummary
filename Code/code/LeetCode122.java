package com.todd.code;

/**
 * @author todd
 * @date 2020/5/18 13:52
 * @description: 买卖股票的最佳时机 II
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 */
public class LeetCode122 {
    /**
     * 贪心策略，所有的涨势值全部加入。
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < len; i++) {
            ans += prices[i] - prices[i - 1] > 0 ? prices[i] - prices[i - 1] : 0;
        }
        return ans;
    }

    /**
     * 动态规划，两种状态，当前持有股票与当前不持有股票。
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return Math.max(dp[len - 1][0], dp[len - 1][1]);
    }

    /**
     * 动态规划，两种状态，当前持有股票与当前不持有股票。
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int cash = 0;
        int hold = -prices[0];
        for (int i = 1; i < len; i++) {
            cash = Math.max(cash, hold + prices[i]);
            hold = Math.max(hold, cash - prices[i]);
        }
        return Math.max(hold, cash);
    }
}
