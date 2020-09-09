package com.todd.code;

/**
 * @author todd
 * @date 2020/6/28 7:14
 * @description: 朋友圈
 * 1. dfs搜索
 * 2. 并查集
 */
public class LeetCode547 {
    /**
     * dfs搜索
     * @param M
     * @return ans
     */
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }

        int N = M.length;
        boolean[] visited = new boolean[N];
        int ans = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(M, visited, i);
                ans++;
            }
        }
        return ans;
    }

    public void dfs(int[][] M, boolean[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(M, visited, j);
            }
        }
    }


    /**
     * 并查集
     * @param M
     * @return ans
     */
    public int findCircleNum2(int[][] M) {
        int len = M.length;
        int[] parent = new int[len];
        for (int i = 0; i < len; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (M[i][j] == 1) {
                    int x = find(parent, i);
                    int y = find(parent, j);
                    parent[x] = y;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < len; i++) {
            if (parent[i] == i) {
                ans++;
            }
        }
        return ans;
    }

    private int find(int[] parent, int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }
}
