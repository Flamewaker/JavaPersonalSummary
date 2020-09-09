package com.todd.redo.Star;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/19 12:45
 * @description: 实现一个只包含+，-，*，/的数学运算
 */
public class Calculate {
    public static void main(String[] args) {
        System.out.println(calculate("1+2*3*3/5+4/2"));
    }
    public static int calculate(String str) {
        LinkedList<Integer> numStack = new LinkedList<>();
        LinkedList<Character> opStack = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                if (!opStack.isEmpty() && (opStack.peekLast() == '*' || opStack.peekLast() == '/')) {
                    int a = numStack.pollLast();
                    int b = str.charAt(i) - '0';
                    if (opStack.peekLast() == '*') {
                        numStack.offerLast(a * b);
                    } else {
                        numStack.offerLast(a / b);
                    }
                    opStack.pollLast();
                } else {
                    numStack.offerLast(str.charAt(i) - '0');
                }
            } else {
                opStack.offerLast(str.charAt(i));
            }

        }
        while (!opStack.isEmpty()) {
            char opt = opStack.pollFirst();
            int a = numStack.pollFirst();
            int b = numStack.pollFirst();
            if (opt == '+') {
                numStack.offerFirst(a + b);
            } else {
                numStack.offerFirst(a - b);
            }
        }
        return numStack.pollLast();
    }
}
