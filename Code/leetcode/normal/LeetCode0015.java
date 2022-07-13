package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和，
 *
 * @Author todd
 * @Date 2020/4/13
 *
 * 1. 特判，对于数组长度 nn，如果数组为 nullnull 或者数组长度小于 33，返回 [][]。
 * 2. 对数组进行排序。
 * 3. 遍历排序后数组：
 * 若 nums[i]>0：因为已经排序好，所以后面不可能有三个数加和等于 0，直接返回结果。
 * 对于重复元素：跳过，避免出现重复解
 * 令左指针 L=i+1，右指针 R=n-1，当 L<R 时，执行循环：
 *      当 nums[i]+nums[L]+nums[R]==0，执行循环，判断左界和右界是否和下一位置重复，去除重复解。并同时将 L,R 移到下一位置，寻找新的解
 *      若和大于 0，说明 nums[R] 太大，R 左移
 *      若和小于 0，说明 nums[L] 太小，L 右移
 *
 */
public class LeetCode0015 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        if (nums == null || len < 3) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < len - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return ans;
    }
}
