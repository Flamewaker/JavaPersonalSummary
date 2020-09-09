package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/2 10:26
 * @description: 牛牛爱花
 * 牛牛有一个3*n的土地。这个土地有3行，n列。牛牛想在土地上种至少一朵花。
 * 为了花儿能够茁壮成长，每一朵花的上下左右四个方向不能有其他的花。问有多少种种花的方案。
 * 为防止答案过大，答案对1e9+7取模。
 *
 * 用二进制[0~7]代表每一列的种花情况。用代表有列且最后一列的种花情况为dp[i][mask]，通过枚举有列的最后一列花的情况来转移。可以用滚动数组优化空间复杂度。
 * 简单版的状态压缩问题。
 * 只存五种情况分别是 000, 001, 010, 100, 101
 * 注意要减去全部为0的情况。
 */
public class NC528 {
    public int solve (int n) {
        int mod = 1000000007;
        long[][] dp = new long[n][5];
        for (int i = 0; i < 5; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][3] + dp[i - 1][4]) % mod;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2] + dp[i - 1][3]) % mod;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][3] + dp[i - 1][4]) % mod;
            dp[i][3] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2]) % mod;
            dp[i][4] = (dp[i - 1][0] + dp[i - 1][2]) % mod;
        }

        int res = (int)((dp[n - 1][0] + dp[n - 1][1] + dp[n - 1][2] + dp[n - 1][3] + dp[n - 1][4] - 1) % mod);

        return res;
    }
}
