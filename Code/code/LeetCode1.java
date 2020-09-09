package com.todd.code;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @Author todd
 * @Date 2020/4/13
 */
public class LeetCode1 {

    //给定一个未排序的数组，找到两个数使得它们相加之和等于目标数。

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

    //给定一个已按照 升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。

    public int[] twoSum2(int[] numbers, int target) {
        if (numbers == null) {
            return null;
        }
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];
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
