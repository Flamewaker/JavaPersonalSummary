package com.todd;

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
 * 非进位和：异或运算 n = a ^ b
 * 进位：与运算 + 左移一位 c = (a & b) << 1
 * sum = a + b => n + c ，由于不能使用加法，因此再进行与操作，将进位加到前面的数中。循环 n 和 c 直到 c = 0, 返回sum
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer65 {
    public int add(int a, int b) {
        while (b != 0) {
            int c = (a & b) << 1;
            a ^= b;
            b = c;
        }
        return a;
    }
}
