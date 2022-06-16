package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/24 9:22
 * @description: 131. 分割回文串
 * 区间dp + 深搜
 */
public class LeetCode131 {
    public List<List<String>> partition(String s) {
        if (s.length() == 0) {
            return new ArrayList<>();
        }

        char[] str = s.toCharArray();
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        for (int r = 0; r < n; r++) {
            for (int l = 0; l <= r; l++) {
                if (str[l] == str[r] && (r - l < 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                }
            }
        }

        List<List<String>> ans = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        dfs(s, dp, 0, n, temp, ans);
        return ans;
    }

    public void dfs(String str, boolean[][] dp, int index, int n, List<String> temp, List<List<String>> ans) {
        if (index >= n) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = index; i < n; i++) {
            if (dp[index][i]) {
                temp.add(str.substring(index, i + 1));
                dfs(str, dp, i + 1, n, temp, ans);
                temp.remove(temp.size() - 1);
            }
        }
    }

}
