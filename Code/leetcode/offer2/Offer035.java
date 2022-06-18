package com.todd.leetcode.offer2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 035. 最小时间差
 * @date 10:22 AM 2022/6/17
 */
public class Offer035 {
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        if (n > 1440) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        int minutes[] = new int[n];
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(timePoints.get(i).split(":")[0]);
            int b = Integer.parseInt(timePoints.get(i).split(":")[1]);
            minutes[i] = a * 60 + b;
        }
        Arrays.sort(minutes);
        for (int i = 1; i < n; i++) {
            ans = Math.min(ans, minutes[i] - minutes[i - 1]);
        }
        return Math.min(ans, 1440 - minutes[n - 1] + minutes[0]);
    }
}
