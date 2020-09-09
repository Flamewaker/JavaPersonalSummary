package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 12:06
 * @description: 剑指 Offer 14- I. 剪绳子
 */
public class Offer14_1 {
    public int cuttingRope(int n) {
        if (n <= 3) {
            return n;
        }
        int a = n / 3, b = n % 3;
        if (b == 0) {
            return (int) Math.pow(3, a);
        } else if (b == 1) {
            return (int) Math.pow(3, a - 1) * 4;
        } else {
            return (int) Math.pow(3, a) * 2;
        }
    }
}
