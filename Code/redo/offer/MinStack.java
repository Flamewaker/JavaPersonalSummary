package com.todd.redo.offer;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/25 6:48
 * @description: 剑指 Offer 30. 包含min函数的栈
 */
public class MinStack {
    LinkedList<Integer> stack;
    LinkedList<Integer> minStack;

    public MinStack() {
        stack = new LinkedList<>();
        minStack = new LinkedList<>();
    }



    public void push(int val) {
        stack.offerLast(val);
        if (minStack.isEmpty() || minStack.peekLast() >= val) {
            minStack.offerLast(val);
        }
    }

    public int top() {
        return stack.peekLast();
    }

    public int min() {
        return minStack.peekLast();
    }

    public void pop() {
        if (minStack.peekLast().equals(stack.peekLast())) {
            minStack.pollLast();
        }
        stack.pollLast();
    }
}
