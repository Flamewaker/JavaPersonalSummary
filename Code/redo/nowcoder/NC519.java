package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 14:05
 * @description: TODO
 *
 * 牛妹是一家口罩厂家的老板，由于现在疫情严重，牛妹想重新分配每条生产线上的人数来使得能生产的口罩最多。
 * 牛妹所在的公司一共有mm名员工，nn条生产线(0.....n-1)，每条生产线有strategy[i].size种人数安排策略。例如：3个人在a生产线上，a生产线每天生产8个口罩；5个人在aa生产线上，每天a生产线能生产15个口罩。
 * 牛妹想知道通过合理的策略安排最多每天能生产多少口罩？（可以不用将所有员工都分配上岗，生产线可以选择闲置）
 *
 */
public class NC519 {
    public class Point {
        int x;
        int y;
    }

    public int producemask(int n, int m, Point[][] strategy) {
        // write code here
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                //当前状态下可能不选取任何策略。
                dp[i][j] = dp[i - 1][j];
                //枚举每一种策略，然后选取当前状态的最优解。
                for (Point p : strategy[i - 1]) {
                    if (j < p.x) {
                        continue;
                    }
                    dp[i][j] = Math.max(dp[i - 1][j - p.x] + p.y, dp[i][j]);
                }
            }
        }
        return dp[n][m];
    }
}
