package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tongchengdong
 * @description 56. 合并区间 (*)
 * 整体思路：
 * 贪心
 * 1. 区间按照左边界进行排序，从而能够方便进行区间合并。
 * 2. 子过程：从当前所在的第一个区间开始向后搜索，获得当前区间及之后区间能够合并的最大的一个区间。
 * @date 10:04 AM 2022/5/22
 */
public class LeetCode56 {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }

        // 1. 区间按照左边界进行排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int n = intervals.length;
        int i = 0;
        List<int[]> ans = new ArrayList<>();

        while (i < n) {
            // 2. 从当前所在的第一个区间开始向后搜索
            int left = intervals[i][0];
            int right = intervals[i][1];

            // 3. 获得当前区间及之后区间能够合并的最大的一个区间, 判断当前区间能否合并
            while (i + 1 < n && right >= intervals[i + 1][0]) {
                // 3.1 获取合并区间范围
                right = Math.max(right, intervals[i + 1][1]);
                i++;
            }

            // 4. 记录当前合并区间的结果
            ans.add(new int[]{left, right});

            // 5. 开启下一轮循环
            i++;
        }
        return ans.toArray(new int[ans.size()][2]);
    }
}
