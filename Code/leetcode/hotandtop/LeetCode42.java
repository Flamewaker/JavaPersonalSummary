package com.todd.leetcode.hotandtop;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 42. 接雨水
 * 整体思路：
 * 1. 暴力 时间O(N^2) 空间O(1) ：该柱子的左右两侧最大高度的较小者减去此柱子的高度。因此我们只需要遍历每个柱子，累加每个柱子可以储水的高度即可。
 * 2. 双指针  时间O(N) 空间O(1) ：我们都需要从两头重新遍历一遍求出左右两侧的最大高度，这里是有很多重复计算的，很明显最大高度是可以记忆化的，具体在这里可以用数组边递推边存储。
 * 3. 单调栈  时间O(N) 空间O(N) ：当前柱子如果小于等于栈顶元素，说明形不成凹槽，则将当前柱子入栈；反之若当前柱子大于栈顶元素，说明形成了凹槽，于是将栈中小于当前柱子的元素pop出来，将凹槽的大小累加到结果中。
 * @date 12:39 AM 2022/6/1
 */
public class LeetCode42 {
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int n = height.length;
        int ans = 0;
        int[][] dp = new int[n][2];
        dp[0][0] = height[0];
        dp[n - 1][1] = height[n - 1];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], height[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            dp[i][1] = Math.max(dp[i + 1][1], height[i]);
        }
        for (int i = 0; i < n; i++) {
            ans += Math.min(dp[i][0], dp[i][1]) - height[i];
        }
        return ans;
    }

    public int trap2(int[] heights) {
        int len = heights.length;
        if (len == 0 || len == 1 || len == 2) {
            return 0;
        }
        int ans = 0;
        Deque<Integer> st = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            while (!st.isEmpty() && heights[i] > heights[st.peekLast()]) {
                int cur = st.peekLast();
                while (!st.isEmpty() && heights[st.peekLast()] == heights[cur]) {
                    st.pollLast();
                }
                if (!st.isEmpty()) {
                    int last = st.peekLast();
                    ans += (Math.min(heights[i], heights[last]) - heights[cur]) * (i - last - 1);
                    System.out.println(i + " " + (Math.min(heights[i], heights[last]) - heights[cur]) * (i - last - 1));
                }
            }
            st.offerLast(i);
        }
        System.out.println(st.size());
        return ans;
    }
}
