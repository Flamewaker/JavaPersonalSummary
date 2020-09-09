package com.todd.code;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 * 思路: 利用一个滑动窗口（双指针）。
 * 如果sum < target, 右指针可以向右移动。
 * 如果sum > target, 左指针需要往右移动，不存在以该点为左节点和为target的值。
 * 如果sum == target, 将答案放到数组中。
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer57_2 {
    public int[][] findContinuousSequence(int target) {
        int i = 1;
        int j = 1;
        int sum = 0;
        List<int[]> ans = new ArrayList<>();
        while (i <= target / 2) {
            if (sum < target) {
                sum += j;
                j++;
            } else if (sum > target) {
                sum -= i;
                i++;
            } else {
                int[] cur = new int[j - i];
                for (int z = 0; z < j - i; z++) {
                    cur[z] = i + z;
                }
                ans.add(cur);
                sum -= i;
                i++;
            }

        }
        return ans.toArray(new int[ans.size()][]);
    }
}
