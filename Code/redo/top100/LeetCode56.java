package com.todd.redo.top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/22 9:40
 * @description: TODO
 */
public class LeetCode56 {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }
        List<int[]> ans = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int i = 0;
        while (i < intervals.length) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            while (i + 1 < intervals.length && intervals[i + 1][0] <= right) {
                right = Math.max(right, intervals[i + 1][1]);
                i++;
            }
            ans.add(new int[]{left, right});
            i++;
        }
        return ans.toArray(new int[ans.size()][2]);
    }
}
