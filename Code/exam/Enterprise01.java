package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/31 21:49
 * @description: 连续子数组最大和
 */
public class Enterprise01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        System.out.println(solve(nums));
    }

    public static int solve(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        int ans = Integer.MIN_VALUE;
        int curr = 0;
        for (int i = 0; i < length; i++) {
            curr = curr < 0 ? nums[i] : curr + nums[i];
            ans = Math.max(curr, ans);
        }
        return ans;
    }
}
