package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 123. 买卖股票的最佳时机 III
 * 整体思路：
 * 1. 分几个阶段: 未买 -> 第一次买 -> 第一次卖 -> 第二次买 -> 第二次卖
 * 2. dp[n][2 + 1][2] 分别指代每一个阶段的计算结果 第一维为数组长度，第二维为所处阶段，第三维为当前存有股票还是没有股票
 * 3. dp[n][k][0] = dp[n - 1][k][1] + prices[n]; 从持有股票到卖出股票
 * 4. dp[n][k][1] = dp[n - 1][k - 1][0] - prices[n]; 买入股票
 * @date 2:58 PM 2022/5/29
 */
public class LeetCode123 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int[][][] dp = new int[prices.length][2 + 1][2];
        // 当 i = 0，手里没股票，最大利润为0，注意 k = 2 的初始化指代只进行一次股票买卖
        dp[0][1][0] = 0;
        dp[0][2][0] = 0;
        // 当 i = 0，手里有股票，因为还没有盈利，最大利润为 -prices[i]
        dp[0][1][1] = -prices[0];
        dp[0][2][1] = -prices[0];

        //k需要减的情况是，卖出了股票
        for (int i = 1; i < prices.length; i++) {
            for (int k = 1; k <= 2; k++) {
                // 第i天，当前处于第k阶段，手里没有股票情况
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                // 第i天，当前处于第k阶段，手里有股票情况
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }
        return dp[prices.length - 1][2][0];
    }
}
