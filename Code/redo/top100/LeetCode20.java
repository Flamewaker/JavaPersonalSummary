package com.todd.redo.top100;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/7 10:41
 * @description: TODO
 */
public class LeetCode20 {
    public boolean isValid(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
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
        return stack.isEmpty() ? true : false;
    }
}
