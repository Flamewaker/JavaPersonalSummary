package com.todd.code;

/**
 * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。
 * 一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer13 {
    // public int movingCount(int m, int n, int k) {
    //     boolean[][] visited = new boolean[m][n];
    //     LinkedList<int[]> queue = new LinkedList<>();
    //     int count = 0;
    //     queue.add(new int[]{0, 0});
    //     while(!queue.isEmpty()){
    //         int[] index = queue.poll();
    //         if(index[0] >= 0 && index[1] >= 0 && index[0] < m && index[1] < n && !visited[index[0]][index[1]] &&sum(index[0], index[1]) <= k){
    //             count++;
    //             visited[index[0]][index[1]] = true;
    //             queue.add(new int[]{index[0] + 1, index[1]});
    //             queue.add(new int[]{index[0], index[1] + 1});
    //         }
    //     }
    //     return count;

    // }

    int m, n, k;
    boolean[][] visited;
    public int movingCount(int m, int n, int k) {
        this.m = m; this.n = n; this.k = k;
        this.visited = new boolean[m][n];
        return dfs(0, 0);
    }
    public int dfs(int i, int j) {
        if(i < 0 || i >= m || j < 0 || j >= n || k < sum(i, j) || visited[i][j]) return 0;
        visited[i][j] = true;
        return 1 + dfs(i + 1, j) + dfs(i, j + 1);
    }

    public int sum(int m, int n){
        int sum = 0;
        while(m != 0){
            sum += m % 10;
            m /= 10;
        }
        while(n != 0){
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}
