package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 121. 买卖股票的最佳时机
 * 整体思路：
 * 1. 由于只有一次买卖机会，若想要获得最大利润，就需要得到最大的差值。贪心算法。
 * 2. 遍历数组，记录当前价格之前的最小价格，然后更新最大利润。
 * @date 4:36 PM 2022/5/22
 */
public class LeetCode121 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        // 记录当前最小价格
        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            ans = Math.max(ans, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return ans;
    }

    public int maxProfitOptimized(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        // 记录当前最小价格
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
