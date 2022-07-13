package com.todd.leetcode.normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @author todd
 * @date 2020/6/16 20:10
 * @description: 给定一个整数数组，判断是否存在重复元素。如果任意一值在数组中出现至少两次，函数返回true 。如果数组中每个元素都不相同，则返回 false。
 * easy
 */
public class LeetCode0217 {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int x: nums) {
            if (set.contains(x)) {
                return true;
            }
            set.add(x);
        }
        return false;
    }
}
