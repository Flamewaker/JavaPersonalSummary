package com.todd.code;

/**
 * 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
 * 完全背包。
 * dp[i][0] 前i个硬币形成金额为0的组合数，为1。那就是不选任何硬币，只有这1种选法。
 * dp[0][j] dp[0][j]其中 j ≠ 0 前0种硬币组成目标金额为j的组合数。没有任何情况能够组成，因此结果是0。
 *
 * 这个问题与爬楼梯的问题不同，前一个是求组合数，后一个是求排列数。因此其俩层循环的条件不同。
 * 前者是当未使用当前元素时对结果的影响情况。后一个是对每个值产生一次元素遍历。下面会附上爬楼梯的模板。
 *
 * @Author todd
 * @Date 2020/4/23
 */
public class LeetCode518 {
    public int change(int amount, int[] coins) {
        int len = coins.length;
        int[][] dp = new int[len + 1][amount + 1];
        for (int i = 0; i <= len; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= amount; i++) {
            dp[0][i] = 0;
        }

        for(int i = 1; i <= len; i++){
            for(int j = 1; j <= amount; j++){
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i - 1]) {
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[len][amount];
    }

    public int change2(int amount, int[] coins) {
        int len = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for(int i = 0; i < len; i++){
            for(int j = coins[i]; j <= amount; j++){
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        int[] steps = {1,2};
        for (int i = 1; i <= n; i++){
            for (int j = 0; j < 2; j++){
                if (i < steps[j]) {
                    continue;
                }
                dp[i] = dp[i] + dp[i-steps[j]];
            }
        }
        return dp[n];

    }

}
