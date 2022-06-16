package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 122. 买卖股票的最佳时机 II
 * 整体思路：贪心
 * 1. 将所有上涨的阶段累加起来
 * @date 2:51 PM 2022/5/29
 */
public class LeetCode122 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int sum = 0;
        int last = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > last) {
                sum += prices[i] - last;
            }
            last = prices[i];
        }
        return sum;
    }

    public int maxProfitSimplified(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans  += prices[i] - prices[i - 1];
            }
        }
        return ans;
    }
}
