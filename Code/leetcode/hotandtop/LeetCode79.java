package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 79. 单词搜索 (*)
 * 整体思路：
 * 1. 从当前位置进行dfs搜索。
 * @date 12:11 PM 2022/5/22
 */
public class LeetCode79 {
    int row, col;
    boolean[][] visited;
    public boolean exist(char[][] board, String word) {
        if (board == null || board[0] == null || board.length == 0 || board[0].length == 0 || word == null || word.equals("")) {
            return false;
        }
        row = board.length;
        col = board[0].length;
        visited = new boolean[row][col];
        char[] words = word.toCharArray();
        // 1. 搜索整个数组
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                // 2. 从当前位置搜索单词
                if(dfs(i, j, board, words, 0)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(int i, int j, char[][] board, char[] words, int index) {
        if(i < 0 || j < 0 || i >= row || j >= col || visited[i][j] || board[i][j] != words[index]) {
            return false;
        }
        if(index == words.length - 1) {
            visited[i][j] = true;
            return true;
        } else {
            visited[i][j] = true;
            if (dfs(i - 1, j, board, words, index + 1) ||
                    dfs(i, j - 1, board, words, index + 1) ||
                    dfs(i + 1, j, board,  words, index + 1) ||
                    dfs(i, j + 1, board, words, index + 1)) {
                return true;
            } else{
                visited[i][j] = false;
                return false;
            }
        }
    }
}
