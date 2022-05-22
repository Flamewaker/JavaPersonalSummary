package com.todd.leetcode.hotandtop;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 136. 只出现一次的数字
 * 整体思路：
 * 1. 第一种思路：通过 HashMap<Integer, Integer> 统计每一种数字的出现个数, 再判断出现一次的数字 O(n) + O(n)
 * 2. 第二种思路：位运算(异或)，x ^ x
 * @date 5:49 PM 2022/5/22
 */
public class LeetCode136 {
    public int singleNumber(int[] nums) {
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ans ^= nums[i];
        }
        return ans;
    }

    public int singleNumberHash(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Integer num : map.keySet()) {
            Integer count = map.get(num);
            if (count == 1) {
                return num;
            }
        }
        return -1;
    }
}
