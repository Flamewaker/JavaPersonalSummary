package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/28 15:06
 * @description: 连续最大和
 * 一个数组有 N 个元素，求连续子数组的最大和。 例如：[-1,2,1]，和最大的连续子数组为[2,1]，其和为 3
 */
public class DiDi3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] num = new int[n];
        for(int i=0;i<n;i++){
            num[i] = sc.nextInt();
        }
        System.out.println(subArraySum(num));
    }

    public static int subArraySum(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        dp[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < length; i++) {
            dp[i] = dp[i - 1] <= 0 ? nums[i] : dp[i - 1] + nums[i];
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    public static int subArraySum2(int[] nums) {
        int length = nums.length;
        int sum = nums[0];
        int ans = nums[0];
        for (int i = 1; i < length; i++) {
            sum = sum <= 0 ? nums[i] : sum + nums[i];
            ans = Math.max(ans, sum);
        }

        return ans;
    }
}
