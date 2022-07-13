package com.todd.leetcode.normal;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/7/26 13:40
 * @description: 503. 下一个更大元素 II
 * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
 * 使用单调栈来解决
 */
public class LeetCode0503 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,1};
        int[] ans =  nextGreaterElements(nums);
        System.out.println(Arrays.toString(ans));
    }

    public static int[] nextGreaterElements(int[] nums) {
        if (nums.length == 0) {
            return new int[0];
        }
        int length = nums.length;
        LinkedList<Integer> stack = new LinkedList<>();
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = -1;
        }
        for (int i = 0; i < 2 * length; i++) {
            int k = i % length;
            while (!stack.isEmpty() && nums[k] > nums[stack.peekLast()]) {
                if (ans[stack.peekLast()] == -1) {
                    int index = stack.pollLast();
                    ans[index] = nums[k];
                } else {
                    stack.pollLast();
                }
            }
            stack.offerLast(k);
        }
        return ans;
    }
}
