package com.todd.code;

import java.util.LinkedList;

/**
 * Offer09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class CQueue {
    LinkedList<Integer> s1;
    LinkedList<Integer> s2;

    public CQueue() {
        s1 = new LinkedList<>();
        s2 = new LinkedList<>();
    }

    public void appendTail(int value) {
        s1.add(value);
    }

    public int deleteHead() {
        if(!s2.isEmpty()){
            return s2.pop();
        } else if(!s1.isEmpty()){
            while(!s1.isEmpty()){
                s2.addLast(s1.pop());
            }
            return s2.pop();
        } else {
            return -1;
        }

    }
}
