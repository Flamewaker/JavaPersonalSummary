package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tongchengdong
 * @description 39. 组合总和
 * 整体思路：
 * 1. dfs模板题
 * 2. 终止条件 sum == target || index = length
 * 3. 循环条件 [0, (target - currSum) / candidates[i]]
 * @date 9:39 PM 2022/5/21
 */
public class LeetCode39 {

    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return ans;
        }
        searchCombinationSum(candidates, target, 0, 0, new ArrayList<>());
        return ans;
    }

    private void searchCombinationSum(int[] candidates, int target, int currSum, int index, List<Integer> cur) {
        if (currSum == target) {
            ans.add(cur);
            return;
        }
        if (index == candidates.length) {
            return;
        }
        for (int i = 0; i <= (target - currSum) / candidates[index]; i++) {
            List<Integer> list = new ArrayList<>(cur);
            for (int j = 0; j < i; j++) {
                list.add(candidates[index]);
            }
            searchCombinationSum(candidates, target, currSum + candidates[index] * i, index + 1, list);
        }
    }

    /**
     * 这里先进行排序，在之后的遍历过程中可以通过 candidates[index] > nextTarget
     */
    public List<List<Integer>> combinationSumOptimized(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return ans;
        }
        int len = candidates.length;
        Arrays.sort(candidates);
        dfsOptimized(candidates, 0, target, new ArrayList<>());
        return ans;
    }

    public void dfsOptimized(int[] candidates, int index, int nextTarget, List<Integer> cur){
        if(nextTarget == 0){
            ans.add(cur);
        }
        // 注意这里的剪枝逻辑, 由于排序过了，因此 candidates[index] 后的数字均不成立可以被剪枝
        if(index >= candidates.length || candidates[index] > nextTarget){
            return;
        }
        for(int i = 0; i <= nextTarget / candidates[index]; i++){
            List<Integer> list = new ArrayList<>(cur);
            for(int j = 0; j < i; j++){
                list.add(candidates[index]);
            }
            dfsOptimized(candidates, index + 1, nextTarget - i * candidates[index], list);
        }
    }
}
