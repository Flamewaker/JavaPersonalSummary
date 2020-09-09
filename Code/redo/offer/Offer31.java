package com.todd.redo.offer;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/25 7:17
 * @description: 剑指 Offer 31. 栈的压入、弹出序列
 */
public class Offer31 {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        LinkedList<Integer> stack = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < pushed.length; i++) {
            stack.offerLast(pushed[i]);
            while (index < popped.length && !stack.isEmpty() && popped[index] == stack.peekLast()) {
                stack.pollLast();
                index++;
            }
        }
        return index == popped.length;
    }
}
