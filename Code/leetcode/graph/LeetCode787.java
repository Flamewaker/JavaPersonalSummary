package com.todd.leetcode.graph;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/8/5 12:53
 * @description: 787. K 站中转内最便宜的航班
 * 有 n 个城市通过 m 个航班连接。每个航班都从城市 u 开始，以价格 w 抵达 v。
 * 现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，你的任务是找到从 src 到 dst 最多经过 k 站中转的最便宜的价格。 如果没有这样的路线，则输出 -1。
 * Dijkstra : 寻找从源到目的地的最低花费
 * (k + 1) * 1000 + i : 做了一个编码把不同长度的路径都记录下来。因为优先队列里可能保存了到同一个点的多种可能性，如果只使用place的话，就会把原值覆盖，当k不满足条件时，这条路径就废了，又因为cost比另一条路径要小，所以另一条路径不会被当作最优解，也废掉了，从而导致答案遗漏。
 */
public class LeetCode787 {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] graph = new int[n][n];
        for (int[] flight : flights) {
            graph[flight[0]][flight[1]] = flight[2];
        }
        HashMap<Integer, Integer> best = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, src});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], k = cur[1], place = cur[2];
            if (k > K + 1 || cost > best.getOrDefault(k * 1000 + place, Integer.MAX_VALUE)) {
                continue;
            }
            if (place == dst) {
                return cost;
            }
            for (int i = 0; i < n; i++) {
                if (graph[place][i] > 0) {
                    int newcost = cost + graph[place][i];
                    if (newcost < best.getOrDefault((k + 1) * 1000 + i, Integer.MAX_VALUE)) {
                        pq.offer(new int[]{newcost, k + 1, i});
                        best.put((k + 1) * 1000 + i, newcost);
                    }
                }
            }
        }


        return -1;
    }
}
