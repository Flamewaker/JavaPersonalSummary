package com.todd.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 9:46
 * @description: 众所周知，牛能和牛可乐经常收到小粉丝们送来的礼物，每个礼物有特定的价值，他俩想要尽可能按照自己所得价值来平均分配所有礼物。
 * 那么问题来了，在最优的情况下，他俩手中得到的礼物价值和的最小差值是多少呢？
 * p.s 礼物都很珍贵，所以不可以拆开算哦
 *
 * 01背包，价值和重量相等问题
 * 我们取礼物价值总和数的一半（向上取）来作为我们背包的容量，我们要尽可能地去填满这个背包，这样差值就是最小
 */
public class NC509 {
    public static int maxPresent (int[] presentVec) {
        int n = presentVec.length;
        if (n == 0) {
            return 0;
        }
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += presentVec[i];
        }
        //简化为背包问题
        int v = (sum + 1) / 2;
        int[] dp = new int[v + 1];
        for (int i = 0; i < n; i++) {
            int p = presentVec[i];
            for (int j = v; j >= p; j--) {
                dp[j] = Math.max(dp[j], dp[j - p] + p);
            }
        }
        return Math.abs(sum - 2 * dp[v]);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{41,467,334,1,169,224,478,358};
        System.out.println(maxPresent2(nums));
    }

    public static int maxPresent2 (int[] presentVec) {
        int n = presentVec.length;
        if (n == 0) {
            return 0;
        }
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += presentVec[i];
        }
        //简化为背包问题
        int v = (sum + 1) / 2;
        int[][] dp = new int[n + 1][v + 1];
        for (int i = 1; i <= n; i++) {
            int p = presentVec[i - 1];
            for (int j = 1; j <= v; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= p) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - p] + p);
                }
            }
        }
        return Math.abs(sum - 2 * dp[n][v]);
    }
}
