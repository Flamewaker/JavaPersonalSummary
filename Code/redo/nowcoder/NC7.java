package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/9 19:51
 * @description: 买卖股票的最佳的时机
 */
public class NC7 {
    public int maxProfit (int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int curMinValue = prices[0];
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (curMinValue > prices[i]) {
                curMinValue = prices[i];
            }
            ans = Math.max(ans, prices[i] - curMinValue);
        }
        return ans;
    }
}
