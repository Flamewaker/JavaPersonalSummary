package com.todd.leetcode.normal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/8/1 20:34
 * @description: 252 会议室 + 253 会议室 2
 * 252 会议室 给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],...] (si < ei)，请你判断一个人是否能够参加这里面的全部会议。
 *
 * 思路是对区间按照第一位排序，然后遍历排序后的区间，维护一个第二位的最小值，看是否有一个区间额第一位大于该最小值，如果有那么这两个区间肯定不相交。
 *
 * 253 会议室 2 给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],…] (si < ei)，为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
 *
 * 在252的基础上改进，每一个区间是会议的时间，问需要多少个会议室。思路还是延续上一题，先排序，然后遍历。还需要维护一个第二位的最小值，
 * 如果遍历的当前区间的第一位小于等于最小值，那么铁定需要加一个room，那如果大于呢？这时就不需要添加room了，因为它可以等待那一个会议结束用那个会议室，
 * 这时就要把那个最小值改为自己的第二位，这样说来，每一个区间遍历完都需要记录第二位，我们需要得到这些所有第二位的最小值，那么很自然会想到使用最小堆来实现。
 */
public class LeetCode0252 {
    public static boolean isInterval(int[][] intervals){
        if (intervals.length == 0) {
            return true;
        }
        int length = intervals.length;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int end = intervals[0][1];
        for (int i = 1; i < length; i++) {
            if (end > intervals[i][0]) {
                return false;
            }
            end = Math.min(end, intervals[i][1]);
        }
        return true;
    }

    public static int isInterval2(int[][] intervals){
        if (intervals.length == 0) {
            return 0;
        }
        int length = intervals.length;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int count = 1;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 1; i < length; i++) {
            if (!queue.isEmpty() && intervals[i][0] >= queue.peek()) {
                queue.poll();
            }
            queue.offer(intervals[i][1]);
        }
        return queue.size();
    }
}
