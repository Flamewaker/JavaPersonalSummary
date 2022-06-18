package com.todd.leetcode.offer2;

import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 036. 后缀表达式
 * 整体思路：
 * 栈
 * @date 11:32 AM 2022/6/17
 */
public class Offer036 {
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (String t : tokens) {
            if (t.matches("-*[0-9]+")) {
                stack.push(Integer.parseInt(t));
            } else {
                int b = stack.pop();
                int a = stack.pop();
                if ("+".equals(t)) {
                    stack.push(a + b);
                } else if ("-".equals(t)){
                    stack.push(a - b);
                } else if ("*".equals(t)){
                    stack.push(a * b);
                } else if ("/".equals(t)){
                    stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }
}
