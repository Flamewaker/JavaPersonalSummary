package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/29 22:15
 * @description: 57. 插入区间
 * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 * 1. 由于这里有一个判断插入和未插入的状态，所以肯定要定义一个状态。
 * 2. 总共的状态分成3种（1）未插入时的状态。（2）正在进行插入时的状态。（3）插入后的状态。
 * 3. 边界情况是这道题需要考虑的重点：未插入的时候，右边界条件限制。插入后，左边界条件限制。
 * 4. 正在进行插入的过程中，当第一次边界条件不满足的时候，也就是左边界正好不满足的情况下，这时候进行插入。
 */
public class LeetCode0057 {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int length = intervals.length;
        List<int[]> ans = new ArrayList<>();
        int leftWin = newInterval[0], rightWin = newInterval[1];
        boolean inserted = false;
        for (int[] interval : intervals) {
            if (interval[0] > rightWin && !inserted) {
                ans.add(new int[]{leftWin, rightWin});
                inserted = true;
            }
            if (interval[1] < leftWin || interval[0] > rightWin) {
                ans.add(interval);
            } else {
                leftWin = Math.min(leftWin, interval[0]);
                rightWin = Math.max(rightWin, interval[1]);
            }
        }
        if (!inserted) {
            ans.add(new int[]{leftWin, rightWin});
        }
        return ans.toArray(new int[ans.size()][2]);
    }
}
