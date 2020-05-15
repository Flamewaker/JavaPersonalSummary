package com.todd;

/**
 * 我们把只包含因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第n个丑数。
 * 除了暴力处理的思路外（对每个数分别不断/2,/3,/5）。创建数组保存已经找到的丑数，利用较小的空间消耗换取时间效率的提升。
 * 答题思路：三指针思路，由于每个丑数是基于之前的所有的数*2,*3,*5得到的，因此可以对每个位置记录一个指针，
 * 每次得到（*2,*3,*5）能够得到的最小的丑数（前面不可能产生比第K个更大的丑数了），并将其加入到数组中。基于动态规划的思想（不明显）。
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Offer49 {
    public int nthUglyNumber(int n) {
        int p2 = 0, p3 = 0, p5 = 0;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(dp[p2] * 2, Math.min(dp[p3] * 3, dp[p5] * 5));
            if (dp[i] == dp[p2] * 2) {
                p2++;
            }
            if (dp[i] == dp[p3] * 3) {
                p3++;
            }
            if (dp[i] == dp[p5] * 5) {
                p5++;
            }
        }
        return dp[n - 1];
    }
}
