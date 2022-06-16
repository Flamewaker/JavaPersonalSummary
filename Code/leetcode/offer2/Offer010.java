package com.todd.leetcode.offer2;

import java.util.HashMap;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 010. 和为 k 的子数组
 * 整体思路：
 * 数据中存在负数，因此滑动窗口不可行。while窗口内元素超过或者不满足条件时移动，但如果数组存在负数，遇到不满足题意的时候，我们应该移动窗口左边界。
 * 此题可以采用前缀和的方法：当我们循环数组到下标N时，需要用到数组前N-1项的计算的结果
 * @date 11:31 AM 2022/6/14
 */
public class Offer010 {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int pre_sum = 0;
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i : nums) {
            pre_sum += i;
            ans += map.getOrDefault(pre_sum - k, 0);
            map.put(pre_sum, map.getOrDefault(pre_sum, 0) + 1);
        }
        return ans;
    }
}
