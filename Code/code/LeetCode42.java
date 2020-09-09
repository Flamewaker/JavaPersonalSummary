package com.todd.code;

import java.io.BufferedInputStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 单调栈 O(N)。
 *
 * @Author todd
 * @Date 2020/4/4
 *
 * 1. 暴力 时间O(N^2) 空间O(1) ：该柱子的左右两侧最大高度的较小者减去此柱子的高度。因此我们只需要遍历每个柱子，累加每个柱子可以储水的高度即可。
 * 2. 双指针  时间O(N) 空间O(1) ：我们都需要从两头重新遍历一遍求出左右两侧的最大高度，这里是有很多重复计算的，很明显最大高度是可以记忆化的，具体在这里可以用数组边递推边存储。
 * 3. 单调栈  时间O(N) 空间O(N) ：当前柱子如果小于等于栈顶元素，说明形不成凹槽，则将当前柱子入栈；反之若当前柱子大于栈顶元素，说明形成了凹槽，于是将栈中小于当前柱子的元素pop出来，将凹槽的大小累加到结果中。
 */
public class LeetCode42 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNext()) {
            String s = sc.nextLine();
            String[] strs = s.substring(1, s.length() - 1).split(",");
            int[] n = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                n[i] = Integer.parseInt(strs[i]);
            }

            System.out.println(trap(n));
        }

    }

    public static int trap(int[] heights) {
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

    //二刷
    public int trap2(int[] heights) {
        if (heights == null || heights.length <= 2) {
            return 0;
        }
        int len = heights.length;
        int ans = 0;
        int i = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        while (i < len) {
            while (!stack.isEmpty() && heights[i] > heights[stack.peekLast()]) {
                int bottom = heights[stack.pollLast()];
                while (!stack.isEmpty() && bottom == heights[stack.peekLast()]) {
                    stack.pollLast();
                }
                if (!stack.isEmpty()) {
                    int lastTrapIndex = stack.peekLast();
                    ans += (i - lastTrapIndex - 1) * Math.min(heights[lastTrapIndex] - bottom, heights[i] - bottom);
                }
            }
            stack.offerLast(i);
            i++;
        }
        return ans;
    }

    public int trap3(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }
        // 定义二维dp数组
        // dp[i][0] 表示下标i的柱子左边的最大值
        // dp[i][1] 表示下标i的柱子右边的最大值
        int[][] dp = new int[n][2];
        dp[0][0] = height[0];
        dp[n - 1][1] = height[n - 1];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(height[i], dp[i - 1][0]);
        }
        for (int i = n - 2; i >= 0; i--) {
            dp[i][1] = Math.max(height[i], dp[i + 1][1]);
        }
        // 遍历每个柱子，累加当前柱子顶部可以储水的高度，
        // 即 当前柱子左右两边最大高度的较小者 - 当前柱子的高度。
        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            res += Math.min(dp[i][0], dp[i][1]) - height[i];
        }
        return res;
    }

    // res += Math.min(dp[i][0], dp[i][1]) - height[i];
    // res += Math.min(leftMax, rightMax) - height[i];
    // 没太理解，下次再看看。
    public int trap4(int[] height) {
        int res = 0, leftMax = 0, rightMax = 0, left = 0, right = height.length - 1;
        while (left <= right) {
            if (leftMax <= rightMax) {
                leftMax = Math.max(leftMax, height[left]);
                res += leftMax - height[left++];
            } else {
                rightMax = Math.max(rightMax, height[right]);
                res += rightMax - height[right--];
            }
        }
        return res;
    }
}
