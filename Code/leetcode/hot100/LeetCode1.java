package com.todd.leetcode.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 1. 两数之和
 * @date 10:13 PM 2022/5/20
 */
public class LeetCode1 {

    /**
     * 给定一个未排序的数组，找到两个数使得它们相加之和等于目标数。
     * 构建索引map，判断 target - nums[i] 是否在数组中，注意要剔除索引为自己的情况
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
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

    /**
     * 变形: 给定一个已按照 升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
     * 整体思路： 双指针逼近目标值
     */
    public int[] twoSum2(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                return new int[]{i + 1, j + 1};
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }
}
