package com.todd.redo.nowcoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/9/3 22:37
 * @description: 城市 A 新建了 n 个座房子，城市规划处用 n−1 条双向街道将房子连在一起，使得任意两座房子之间有且仅有一条道路可达。
 * 牛牛和牛妹将被随机分到两个房子，现在牛牛想知道，他和牛妹房子的最长路径是多少。
 *
 * 转换成n叉树的直径问题。
 */
public class NC537 {
    HashMap<Integer, List<int[]>> map;
    long ans;
    public long solve (int n, int[] u, int[] v, int[] w) {
        map = new HashMap<>();

        for (int i = 0; i < n - 1; i++) {
            if (!map.containsKey(u[i])) {
                map.put(u[i], new LinkedList<int[]>());
            }
            map.get(u[i]).add(new int[]{v[i], w[i]});

            if (!map.containsKey(v[i])) {
                map.put(v[i], new LinkedList<int[]>());
            }
            map.get(v[i]).add(new int[]{u[i], w[i]});
        }

        ans = 0;
        dfs(1, 0);
        return ans;
    }

    public long dfs(int cur, int pre) {
        long s = 0;
        for (int[] next : map.get(cur)) {
            if (next[0] != pre) {
                long t = dfs(next[0], cur) + next[1];
                ans = Math.max(ans, s + t);
                s = Math.max(s, t);
            }
        }

        return s;

    }
}
