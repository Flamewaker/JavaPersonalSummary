package com.todd.leetcode.offer2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 006. 排序数组中两个数字之和
 * @date 10:21 PM 2022/6/13
 */
public class Offer006 {
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int left = 0, right = n - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[0];
    }
}
