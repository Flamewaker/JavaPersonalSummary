package com.todd.leetcode.graph;

import java.util.*;

/**
 * @author todd
 * @date 2020/8/5 14:01
 * @description: 743. 网络延迟时间
 * 有N个网络节点，标记为1到N。
 * 给定一个列表times，表示信号经过有向边的传递时间。times[i] = (u, v, w)，其中u是源节点，v是目标节点， w是一个信号从源节点传递到目标节点的时间。
 * 现在，我们从某个节点K发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回-1。
 */
public class LeetCode743 {
    public int networkDelayTime(int[][] times, int N, int K) {
        HashMap<Integer, List<int[]>> map;
        HashMap<Integer, Integer> dist = new HashMap<>();
        map = new HashMap<>();
        boolean[] check = new boolean[N + 1];
        for (int[] time : times) {
            if (!map.containsKey(time[0])) {
                map.put(time[0], new ArrayList<int[]>());
            }
            map.get(time[0]).add(new int[]{time[1], time[2]});
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(((o1, o2) -> o1[0] - o2[0]));
        priorityQueue.offer(new int[]{0, K});
        while (!priorityQueue.isEmpty()) {
            int[] temp = priorityQueue.poll();
            int d = temp[0], node = temp[1];
            if (dist.containsKey(node)) {
                continue;
            }
            dist.put(node, d);
            if (map.containsKey(node)) {
                for (int[] next : map.get(node)) {
                    if (!dist.containsKey(next[0])) {
                        priorityQueue.offer(new int[]{d + next[1], next[0]});
                    }
                }
            }
        }
        if (dist.size() != N) {
            return -1;
        }
        int cnt = 0;
        for (int c : dist.values()) {
            cnt = Math.max(c, cnt);
        }
        return cnt;

    }
}
