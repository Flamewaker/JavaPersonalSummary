package com.todd.leetcode.offer1;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。
 * 如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。
 * 例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
 * 思路：DFS
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer12 {
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
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
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
        }else{
            visited[i][j] = true;
            if(dfs(i - 1, j, board, words, index + 1) || dfs(i, j - 1, board, words, index + 1) || dfs(i + 1, j, board,  words, index + 1) || dfs(i, j + 1, board, words, index + 1)) return true;
            else{
                visited[i][j] = false;
                return false;
            }
        }
    }
}
