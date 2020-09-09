package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/27 10:09
 * @description: 122. 买卖股票的最佳时机
 * 贪心算法
 */
public class LeetCode121 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (minPrice > prices[i]) {
                minPrice = prices[i];
            }
            ans = Math.max(ans, prices[i] - minPrice);
        }
        return ans;
    }
}
