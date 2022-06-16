package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Author todd
 * @Date 2020/4/16
 * 给出一个区间的集合，请合并所有重叠的区间。
 *
 * 贪心算法，按照区间的左端点排序，那么在排完序的列表中，可以合并的区间一定是连续的，便可以对相邻区间进行合并。
 * 排序的原因是使得可以合并的区间在一起，方便从前向后的搜索。
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
