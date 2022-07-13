package com.todd.leetcode.normal;

import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/6/27 17:26
 * @description: 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。给定 n 和 k，返回第 k 个排列。
 * 1. 用类似字典树的方式，不断逼近正确值。
 */
public class LeetCode0060 {
    public String getPermutation(int n, int k) {
        StringBuilder s = new StringBuilder();
        int[] resN = new int[10]; //初始化全排列结果
        resN[1] = 1;
        for (int i = 2; i <= 9; i++) {
            resN[i] = resN[i - 1] * i;
        }
        boolean[] visited = new boolean[n + 1];

        PriorityQueue<Integer> pq = new PriorityQueue<>(); //还剩余的数字

        for (int i = 1; i <= n; i++) {
            pq.add(i);
        }

        while (k > 0) {
            int size = pq.size();
            int x = resN[size - 1];
            int offset = k % x;
            int groupIndex = k / x + (offset > 0 ? 1 : 0); // 第几个
            k = offset > 0 ? offset : size - 1 == 0 ? 0 : x;//下一轮剩余的个数
            int i = pq.peek();
            for (; i <= n && groupIndex > 0; i++) {
                if (!visited[i] ) {
                    if (groupIndex == 1) {
                        visited[i] = true;
                        s.append(i);
                        pq.remove(i);
                    }
                    groupIndex--;
                }
            }
        }
        return s.toString();
    }

    public int func(int n) {
        if (n == 1 || n == 0) {
            return 1;
        }
        return func(n - 1) * n;
    }
}

