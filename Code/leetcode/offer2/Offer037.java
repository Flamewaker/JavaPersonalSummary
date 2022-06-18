package com.todd.leetcode.offer2;

import java.util.*;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 037. 小行星碰撞
 * 整体思路：
 * 1. 从左到右进行遍历，通过一个栈维护当前未发生碰撞的小行星。
 * 2. 栈顶弹出表明发生了碰撞。
 * @date 11:48 AM 2022/6/17
 */
public class Offer037 {
    public int[] asteroidCollision(int[] asteroids) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (int curr : asteroids) {
            // 1. 当前小行星向右移动时，是否发生碰撞未知，通过栈来记录中间状态。
            if (curr > 0) {
                stack.push(curr);
                continue;
            }
            // 2. 发生碰撞，较小的行星会爆炸，移除栈顶元素
            while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -curr) {
                stack.pop();
            }
            // 3. 行星大小相同，则都会爆炸
            if (!stack.isEmpty() && stack.peek() == -curr) {
                stack.pop();
                // 4. 没有右移动的小行星时，向左移动的小行星不再会发生碰撞，此时加入结果集中
            } else if (stack.isEmpty() || stack.peek() < 0) {
                stack.push(curr);
            }
        }
        int[] ans = new int[stack.size()];
        int index = stack.size() - 1;
        while (!stack.isEmpty()) {
            ans[index--] = stack.pop();
        }
        return ans;
    }
}
