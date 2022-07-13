package com.todd.leetcode.normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @author todd
 * @date 2020/6/15 22:41
 * @description: 给定一个未排序的整数数组，找出最长连续序列的长度。
 * 要求算法的时间复杂度为 O(n)。
 * 1. 第一个想法就是排序，然后遍历，但时间复杂度为O(nlogn)
 * 2. 第二个思路就是用空间换时间，使用hash表进行查找。但HashMap新增元素的时间复杂度是不固定的，可能的值有O(1)、O(logn)、O(n)。
 */
public class LeetCode0128 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            numSet.add(nums[i]);
        }
        int longest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (numSet.contains(nums[i])) {
                int count = 1;
                int left = nums[i] - 1;
                int right = nums[i] + 1;
                while (numSet.remove(left)) {
                    count++;
                    left--;
                }
                while (numSet.remove(right)) {
                    count++;
                    right++;
                }
                longest = Math.max(longest, count);
            }
        }
        return longest;

    }
}
