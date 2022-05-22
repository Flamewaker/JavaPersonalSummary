package com.todd.leetcode.hotandtop;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tongchengdong
 * @description 139. 单词拆分
 * 整体思路：
 * 1. dp[i][j] = true 指代 [i, j] 内的字符可以由字典拼出来。
 * 2. dp[i][j] = dp[i][k] && dp[k + 1][j]
 * == > 当我们顺序遍历时 dp[i] 指代 [0, i] 可以由字典拼出来。dp[i] = dp[0][k] && dp[k + 1][i] 二维数组可以简化掉。
 * @date 6:14 PM 2022/5/22
 */
public class LeetCode139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return false;
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> wordDictSet = new HashSet<>(wordDict);
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length() + 1];
    }
}
