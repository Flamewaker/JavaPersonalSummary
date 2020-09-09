package com.todd.redo.top100;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/8/5 17:07
 * @description: 1. 两数之和
 */
public class LeetCode1 {
    public int[] twoSum(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[0];
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            if (map.containsKey(target - cur)) {
                return new int[]{map.get(target - cur), i};
            }
            map.put(cur, i);
        }
        return new int[0];
    }
}
