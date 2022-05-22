package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 55. 跳跃游戏 (*)
 * 整体思路：
 * dp
 * 1. 第一种思路：dp[i] = true if (dp[j] = true AND nums[j] > i - j) O(n2)
 * 2. 优化思路： 记录每次能够到达的最大索引位置index, 若能够到达的最大索引位置index不能到达遍历位置，则返回false
 * @date 9:43 AM 2022/5/22
 */
public class LeetCode55 {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] && nums[j] >= i - j) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n - 1];
    }

    public boolean canJumpOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (index < i) {
                return false;
            }
            index = Math.max(index, i + nums[i]);
        }
        return true;
    }
}
