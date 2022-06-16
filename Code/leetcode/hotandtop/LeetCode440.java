package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/8/24 9:50
 * @description: 440. 字典序的第K小数字
 * 给定整数 n 和 k，找到 1 到 n 中字典序第 k 小的数字。
 */
public class LeetCode440 {
    public int findKthNumber(int n, int k) {
        int i = 1;
        k--;
        while (k != 0) {
            long start = i;
            long end = i + 1;
            long num = 0;
            while (start <= n) {
                num += Math.min(end, n + 1) - start;
                start *= 10;
                end *= 10;
            }
            if (num > k) {
                i = i * 10;
                k--;
            } else {
                k -= num;
                i++;
            }
        }
        return i;
    }
}
