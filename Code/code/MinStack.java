package com.todd.code;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Offer30
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 * 辅助栈 辅助栈和数据栈同步或不同步
 * @Author todd
 * @Date 2020/4/18
 * 最弱的方法就是使用链表和优先队列实现。
 */
public class MinStack {
    Deque<Integer> stack;
    Deque<Integer> minValue;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new LinkedList<>();
        minValue = new LinkedList<>();
    }

    /**
     * 辅助栈和数据栈同步
     * @param x
     */
    public void push(int x) {
        stack.offerLast(x);
        if(minValue.isEmpty()){
            minValue.offerLast(x);
        } else {
            minValue.offerLast(Math.min(minValue.peekLast(), x));
        }
    }

    public void pop() {
        stack.pollLast();
        minValue.pollLast();
    }

    public int top() {
        return stack.peekLast();
    }

    public int min() {
        return minValue.peekLast();
    }

    /**
     * 辅助栈和数据栈不同步
     * （1）辅助栈为空的时候，必须放入新进来的数；
     * （2）新来的数小于或者等于辅助栈栈顶元素的时候，才放入，特别注意这里“等于”要考虑进去，因为出栈的时候，连续的、相等的并且是最小值的元素要同步出栈；
     * （3）出栈的时候，辅助栈的栈顶元素等于数据栈的栈顶元素，才出栈。
     *  总结一下：出栈时，最小值出栈才同步；入栈时，最小值入栈才同步。
     * @param x
     */
    public void push2(int x) {
        stack.offerLast(x);
        if(minValue.isEmpty() || minValue.peekLast() >= x){
            minValue.offerLast(x);
        }
    }

    public void pop2() {
        if(minValue.peekLast().equals(stack.peekLast())){
            minValue.pollLast();
        }
        stack.pollLast();
    }
}
