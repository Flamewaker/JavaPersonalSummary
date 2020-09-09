package com.todd.code;

import java.util.Stack;

/**
 * 根据每日气温列表，请重新生成一个列表，对应位置的输出是需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用0来代替。
 * 第一个想法就是针对每个温度值向后进行依次搜索 ，找到比当前温度更高的值，这是最容易想到的办法。原理是从左到右除了最后一个数其他所有的数都遍历一次，
 * 最后一个数据对应的结果肯定是0，就不需要计算。遍历的时候，每个数都去向后数，直到找到比它大的数，数的次数就是对应输出的值。
 *
 * 思路：使用递减栈，遍历整个数组，如果栈不空，且当前数字大于栈顶元素，那么如果直接入栈的话就不是递减栈，所以需要取出栈顶元素，由于当前数字大于栈顶元素的数字，而且一定是第一个大于栈顶元素的数，直接求出下标差就是二者的距离。
 * 继续看新的栈顶元素，直到当前数字小于等于栈顶元素停止，然后将数字入栈，这样就可以一直保持递减栈，且每个数字和第一个大于它的数的距离也可以算出来。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class LeetCode739 {
    public int[] dailyTemperatures(int[] T) {
        if (T == null || T.length == 0) {
            return new int[0];
        }
        Stack<Integer> stack = new Stack();
        int length = T.length;
        int[] result = new int[length];
        stack.push(0);
        for (int i = 1; i < length; i++) {
            while (!stack.empty() && T[stack.peek()] < T[i]) {
                Integer lastIndex = stack.pop();
                result[lastIndex] = i - lastIndex;
            }
            stack.push(i);
        }
        return result;
    }
}
