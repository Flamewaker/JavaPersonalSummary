package com.todd;

/**
 * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer10_1 {
    public int fib(int n) {
        int curr = 0, next = 1, sum;
        for(int i = 0; i < n; i++){
            sum = (curr + next) % 1000000007;
            curr = next;
            next = sum;
        }
        return curr;
    }
}
