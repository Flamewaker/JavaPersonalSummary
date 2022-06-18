package com.todd.leetcode.offer2;

import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 038. 每日温度
 * @date 3:12 PM 2022/6/17
 */
public class Offer038 {
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) {
            return new int[0];
        }
        int n = temperatures.length;
        LinkedList<Integer> stack = new LinkedList<>();
        int[] ans = new int[n];
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int curr = stack.pop();
                ans[curr] = i - curr;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            ans[stack.pop()] = 0;
        }
        return ans;
    }
}
