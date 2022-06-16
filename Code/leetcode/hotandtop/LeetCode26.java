package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/8/7 11:56
 * @description: 26. 删除排序数组中的重复项
 */
public class LeetCode26 {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int length = nums.length;
        int quick = 1;
        int slow = 0;
        while (quick < length) {
            if (nums[quick] != nums[slow]) {
                slow++;
                nums[slow] = nums[quick];
            }
            quick++;
        }
        return ++slow;
    }
}
