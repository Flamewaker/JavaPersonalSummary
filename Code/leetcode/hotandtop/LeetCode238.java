package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 238. 除自身以外数组的乘积
 * 整体思路：
 * 当前位置的结果就是它左部分的乘积再乘以它右部分的乘积。
 * 因此需要进行两次遍历，第一次遍历用于求左部分的乘积，第二次遍历在求右部分的乘积的同时，再将最后的计算结果一起求出来。
 * @date 10:23 AM 2022/5/24
 */
public class LeetCode238 {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return new int[0];
        }
        int n = nums.length;
        int[] ans = new int[n];

        // 1. 从左到右遍历
        int last = 1;
        ans[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            last *= nums[i - 1];
            ans[i] = last;
        }

        // 1. 从右到左遍历
        last = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            last *= nums[i + 1];
            ans[i] *= last;
        }

        return ans;
    }
}
