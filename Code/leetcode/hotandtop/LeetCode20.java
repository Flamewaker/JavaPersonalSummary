package com.todd.leetcode.hotandtop;

import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 20. 有效的括号
 * 整体思路：
 * 简单的栈，判断栈顶和当前的括号是否匹配
 * 注意：pop操作空栈会报异常而pollLast不会，写法有不同
 * @date 2:01 PM 2022/5/21
 */
public class LeetCode20 {
    public boolean isValid(String s) {
        if (s == null || s.length() <= 1) {
            return false;
        }
        int n = s.length();
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            // 1. 当前是 '{' '[' '(' 直接入栈
            // 2. 当前是 '}' ']' ')' 弹出栈顶括号，判断能否匹配
            char c = s.charAt(i);
            if (c == '}') {
                Character t = stack.pollLast();
                if (t == null || !t.equals('{')) {
                    return false;
                }
            } else if (c == ']') {
                Character t = stack.pollLast();
                if (t == null || !t.equals('[')) {
                    return false;
                }
            } else if (c == ')') {
                Character t = stack.pollLast();
                if (t == null || !t.equals('(')) {
                    return false;
                }
            } else {
                stack.offerLast(c);
            }
        }
        return stack.isEmpty();
    }

    public boolean isValidOptimized(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            // 1. 当前是 '{' '[' '(' 直接入栈
            // 2. 当前是 '}' ']' ')' 弹出栈顶括号，判断能否匹配
            if (a == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }
            } else if (a == ']') {
                if (!stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }
            } else if (a == '}') {
                if (!stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
            } else {
                stack.push(a);
            }
        }
        return stack.isEmpty();
    }
}
