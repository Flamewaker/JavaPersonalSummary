package com.todd.leetcode.normal;

import java.util.ArrayList;

/**
 * 给你一个整数数组 nums 和一个整数 k。
 * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
 * 请返回这个数组中「优美子数组」的数目。
 * 滑动窗口，加入俩个哨兵分别坐标是-1， nums.length
 * 注意边界 i = 1 和 i + k - 1 < len - 1
 *
 * @Author todd
 * @Date 2020/4/21
 */
public class LeetCode1248 {

    public static int numberOfSubArrays1(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(-1);
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & 1) == 1) {
                indices.add(i);
            }
        }
        indices.add(nums.length);
        System.out.println(indices.toString());
        int len = indices.size();
        int ans = 0;
        for (int i = 1; i < len - k; i++) {
            int left = indices.get(i) - indices.get(i - 1);
            int right = indices.get(i + k) - indices.get(i + k - 1);
            ans += left * right;

        }
        return ans;
    }


    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 1, 1};
        int[] nums2 = {2, 2, 2, 1, 2, 2, 1, 2, 2, 2};
        System.out.println(numberOfSubArrays1(nums2, 2));
    }
}
