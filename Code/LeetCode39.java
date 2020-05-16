package com.todd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 回溯模板
 *
 * @Author todd
 * @Date 2020/4/13
 */
public class LeetCode39 {
    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<Integer> cur = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, len, target, cur);
        return ans;
    }

    public void dfs(int[] candidates, int index, int nextTarget, List<Integer> cur) {
        if (nextTarget == 0) {
            ans.add(cur);
        }
        if (index == 0 || candidates[0] > nextTarget) {
            return;
        }
        index--;
        for (int i = 0; i <= nextTarget / candidates[index]; i++) {
            List<Integer> list = new ArrayList<>(cur);
            for (int j = 0; j < i; j++) {
                list.add(candidates[index]);
            }
            dfs(candidates, index, nextTarget - i * candidates[index], list);
        }
    }
}
