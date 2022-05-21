package com.todd.leetcode.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tongchengdong
 * @description 15. 三数之和
 * 整体思路：
 * 有序数组双指针，逼近目标值
 * 剪枝情况： 1. nums[left] > 0 这时 nums[mid] + nums[right] > 0， 必不能构成和为0的情况。
 * 2. 重复情况记得剔除掉。
 * @date 2:31 AM 2022/5/21
 */
public class LeetCode15 {
    public List<List<Integer>> threeSum(int[] nums) {
        // 1. 特判，对于数组长度 n，如果数组为 null 或者数组长度小于 3，返回 []
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        // 2. 对数组进行排序
        Arrays.sort(nums);
        int n = nums.length;
        int first = 0;
        int left, right;
        List<List<Integer>> ans = new ArrayList<>();
        System.out.println(first);
        while (first < n - 2) {
            // 3.1 若 nums[i]>0：因为已经排序好，所以后面不可能有三个数加和等于 0，直接返回结果。
            if (nums[first] > 0) {
                break;
            }
            // 3.2 对于重复元素：跳过，避免出现重复解
            if (first > 0 && nums[first] == nums[first - 1]) {
                first++;
                continue;
            }
            // 3.3 令左指针 L=i+1，右指针 R=n-1，当 L<R 时，执行循环
            left = first + 1;
            right = n - 1;
            while (left < right) {
                // 当 nums[i]+nums[L]+nums[R]==0，执行循环，判断左界和右界是否和下一位置重复，去除重复解。并同时将 L,R 移到下一位置，寻找新的解
                // 若和大于 0，说明 nums[R] 太大，R 左移
                // 若和小于 0，说明 nums[L] 太小，L 右移
                int sum = nums[first] + nums[left] + nums[right];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[first], nums[left], nums[right]));
                    while (left < right && nums[right - 1] == nums[right]) {
                        right--;
                    }
                    while (left < right && nums[left + 1] == nums[left]) {
                        left++;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
            first++;
        }
        return ans;
    }
}
