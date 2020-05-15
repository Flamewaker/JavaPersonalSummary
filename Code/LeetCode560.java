package com.todd;

import java.util.HashMap;

/**
 * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
 * 思路 ： 这道题和一般的题不一样的地方在于，nums中的值既有正数又有负数，如果只有正数或只有负数，
 * 可以使用左右指针分别指向一个区间的首尾，维护这个滑动窗口即可。
 * 由于正负数的关系，优化的方式在于利用前缀和的条件。设pre[i]为[0,i]之间的所有数的和，
 * 那么区间[i,j]子数组的和就为pre[j] - pre[i - 1]，若区间和为k，也就是找在pre[j]之前的前缀和是否存在和为pre[j] - k的前缀和。
 * 即pre[i - 1] = pre[j] - k
 * 注意：HashMap优化的时候，初始化将0添加进去。
 *
 * @Auther todd
 * @Date 2020/5/15
 */
public class LeetCode560 {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ans = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int sum = 0;
            for (int j = i; j < len; j++) {
                sum += nums[j];
                if (sum == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int subarraySum2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashMap<Integer, Integer> pre = new HashMap<>();
        pre.put(0, 1);
        int count = 0;
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];

            if (pre.containsKey(preSum - k)) {
                count += pre.get(preSum - k);
            }

            pre.put(preSum, pre.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}
