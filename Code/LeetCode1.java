package com.todd;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @Auther todd
 * @Date 2020/4/13
 */
public class LeetCode1 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            index.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int another = target - nums[i];
            if (index.containsKey(another) && index.get(another) != i) {
                return new int[]{i, index.get(another)};
            }
        }
        return new int[2];
    }
}
