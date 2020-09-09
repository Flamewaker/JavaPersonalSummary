package com.todd.code;

/**
 * @author todd
 * @date 2020/6/27 16:10
 * @description: TODO
 */
public class LeetCode695 {
    public int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == 1){
                    ans = Math.max(ans, dfs(i, j, grid));
                }
            }
        }
        return ans;
    }

    public int dfs(int i, int j, int[][] grid){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0){
            return 0;
        }
        int num = 1;
        grid[i][j] = 0;
        num += dfs(i+1, j, grid);
        num += dfs(i, j+1, grid);
        num += dfs(i-1, j, grid);
        num += dfs(i, j-1, grid);
        return num;
    }
}
