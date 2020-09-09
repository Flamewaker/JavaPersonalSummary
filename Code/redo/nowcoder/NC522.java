package com.todd.redo.nowcoder;

import java.util.*;

/**
 * @author todd
 * @date 2020/9/1 16:59
 * @description: 牛妹出去旅行啦，她准备去N个城市旅行，去每个城市的开销是A_{i}元。但是牛妹有强迫症，她想在去y城市之前先旅游x城市，
 * 于是牛妹列出了这些限制条件list。并且牛妹很节约，她只有V元，她每次会选择当前能去的花费最小的城市,如有多个花费一样的则首先去编号小的城市，
 * 她想知道她最多能到多少个城市去旅游。
 */
public class NC522 {
    class Point {
        int x;
        int y;
    }

    public int Travel (int N, int V, int[] A, Point[] list) {
        if (N == 0 || V == 0) {
            return 0;
        }

        int[] in = new int[N + 1];
        List<List<Integer>> adjacency = new ArrayList<>(N + 1);
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });

        for (int i = 0; i < N + 1; i++) {
            adjacency.add(new LinkedList<>());
        }

        for (int i = 0; i < list.length; i++) {
            int x = list[i].x;
            int y = list[i].y;
            in[y]++;
            adjacency.get(x).add(y);
        }

        for (int i = 1; i < N + 1; i++) {
            if (in[i] == 0) {
                pq.offer(new int[]{i, A[i - 1]});
            }
        }
        int count = 0;
        while (!pq.isEmpty()) {
            int[] city = pq.poll();
            V -= city[1];
            if (V < 0) {
                break;
            }
            count++;
            for (Integer next : adjacency.get(city[0])) {
                in[next]--;
                if (in[next] == 0) {
                    pq.offer(new int[]{next, A[next - 1]});
                }
            }
        }

        return count;
    }
}



