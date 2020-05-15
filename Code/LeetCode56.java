package com.todd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Auther todd
 * @Date 2020/4/16
 */
public class LeetCode56 {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 1) {
            return intervals;
        }
        List<int[]> newIntervals = new ArrayList<>();
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int i = 0;
        int n = intervals.length;
        while (i < n) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            while (i < n - 1 && right >= intervals[i + 1][0]) {
                right = Math.max(right, intervals[i + 1][1]);
                i++;
            }
            newIntervals.add(new int[]{left, right});
            i++;
        }
        return newIntervals.toArray(new int[newIntervals.size()][2]);
    }
}
