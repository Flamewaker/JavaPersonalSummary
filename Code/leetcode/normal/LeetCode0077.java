package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/23 15:18
 * @description: 组合
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 */
public class LeetCode0077 {
    List<List<Integer>> ans = new LinkedList<>();
    List<Integer> temp = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        if (n < k) {
            return ans;
        }
        backTrace(k, n, 0);
        return ans;
    }

    public void backTrace(int remain, int n, int index) {
        if (remain == 0) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for (int i = index + 1; i <= n - remain + 1; i++) {
            temp.add(i);
            backTrace(remain - 1, n, i);
            temp.remove(temp.size() - 1);
        }
    }
}
