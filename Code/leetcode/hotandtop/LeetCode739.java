package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 739. 每日温度
 * 整体思路：
 * 1. 第一个想法就是针对每个温度值向后进行依次搜索 ，找到比当前温度更高的值，这是最容易想到的办法。原理是从左到右除了最后一个数其他所有的数都遍历一次，
 * 最后一个数据对应的结果肯定是0，就不需要计算。遍历的时候，每个数都去向后数，直到找到比它大的数，数的次数就是对应输出的值。
 * 2. 用一个单调递减栈维护未找到升温的索引，不断对当前栈顶元素与当前值比较，进行出栈、入栈和记录索引操作。思路：使用递减栈，遍历整个数组，如果栈不空，且当前数字大于栈顶元素，那么如果直接入栈的话就不是递减栈，所以需要取出栈顶元素，由于当前数字大于栈顶元素的数字，而且一定是第一个大于栈顶元素的数，直接求出下标差就是二者的距离。
 * 继续看新的栈顶元素，直到当前数字小于等于栈顶元素停止，然后将数字入栈，这样就可以一直保持递减栈，且每个数字和第一个大于它的数的距离也可以算出来。
 * @date 3:39 PM 2022/5/29
 */
public class LeetCode739 {
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) {
            return new int[0];
        }
        int n = temperatures.length;
        int[] ans = new int[n];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                Integer index = stack.pop();
                ans[index] = i - index;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Integer index = stack.pop();
            ans[index] = 0;
        }
        return ans;
    }
}
