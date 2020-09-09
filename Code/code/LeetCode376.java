package com.todd.code;

/**
 * *******好题
 * @author todd
 * @date 2020/5/18 11:31
 * @description: 摆动序列
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
 * 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。
 */
public class LeetCode376 {
    public int wiggleMaxLength1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 二维dp数组
        // dp[i][0]表示以下标为i的元素结尾的摆动序列，且序列最后两个元素是递减的最大长度
        // dp[i][1]表示以下标为i的元素结尾的摆动序列，且序列最后两个元素是递增的最大长度
        int[][] dp = new int[nums.length][2];
        int ans = 1;
        dp[0][0] = 1;
        dp[0][1] = 1;
        // 动态规划，时间复杂度O(n*n)，空间复杂度O(2*n)
        for (int i = 1; i < nums.length; i++) {
            // 初始化，最短为1
            dp[i][0] = 1;
            dp[i][1] = 1;
            for (int j = 0; j < i; j++) {
                // 如果递增，尝试更新dp[i][1];
                if (nums[i] > nums[j]) {
                    dp[i][1] = Math.max(dp[j][0] + 1, dp[i][1]);
                    // 如果递减，尝试更新dp[i][0];
                } else if (nums[i] < nums[j]) {
                    dp[i][0] = Math.max(dp[j][1] + 1, dp[i][0]);
                }
                // 如果相等，则不需要操作,结果已经计算过
                // 更新最大值
                ans = Math.max(ans, Math.max(dp[i][0], dp[i][1]));
            }
        }
        return ans;
    }

    public int wiggleMaxLength2(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }
        int[][] dp = new int[n][2];
        dp[0][0] = 1;
        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][0] + 1;
            } else if (nums[i] < nums[i - 1]) {
                dp[i][0] = dp[i - 1][1] + 1;
                dp[i][1] = dp[i - 1][1];
            } else {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1];
            }
        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }

    //DP过程中更新up[i]和down[i]，其实只需要up[i-1]和down[i-1]。
    //直接使用up和down来代替
    public int wiggleMaxLength3(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }
        int up = 1;
        int down = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            }
            if (nums[i] < nums[i - 1]) {
                down = up + 1;
            }
        }
        return Math.max(up, down);
    }
}
