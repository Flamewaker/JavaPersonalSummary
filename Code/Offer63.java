package com.todd;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
 * 动态规划：设动态规划列表 dp， dp[i]代表以 prices[i]为结尾的子数组的最大利润。
 * 由于题目限定 “买卖该股票一次” ，因此前i日最大利润dp[i]等于前i-1日最大利润dp[i-1]和第i日卖出的最大利润中的最大值。
 * 前i日最大利润=max(前(i−1)日最大利润,第i日价格−前i日最低价格) => dp[i]=max(dp[i−1],prices[i]−min(prices[0:i]))
 * 优化： profit=max(profit,prices[i]−min(cost,prices[i])。而在遍历 prices 时，可以借助一个变量（记为成本cost）每日更新最低价格。
 * 变量min保存数组前 i-1 个数字的最小值。
 *
 * @Auther todd
 * @Date 2020/5/14
 */
public class Offer63 {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > min) {
                ans = Math.max(ans, prices[i] - min);
            }
            min = Math.min(min, prices[i]);
        }
        return ans;
    }
}
