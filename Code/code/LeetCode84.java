package com.todd.code;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * 单调栈 + 哨兵
 *
 * @Author todd
 * @Date 2020/4/4
 */
public class LeetCode84 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNext()) {
            String s = sc.nextLine();
            String[] strs = s.substring(1, s.length() - 1).split(",");
            int[] n = new int[strs.length];
            System.out.println(Arrays.toString(n));
            for (int i = 0; i < strs.length; i++) {
                n[i] = Integer.parseInt(strs[i]);
            }

            System.out.println(largestRectangleArea(n));
        }

    }

    public static int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, heights.length);
        newHeights[len + 1] = 0;
        int ans = 0;
        Deque<Integer> st = new LinkedList<>();
        for (int i = 0; i < len + 2; i++) {
            while (!st.isEmpty() && newHeights[i] < newHeights[st.peekLast()]) {
                int cur = st.pollLast();
                while (!st.isEmpty() && newHeights[st.peekLast()] == newHeights[cur]) {
                    st.pollLast();
                }
                ans = Math.max(ans, newHeights[cur] * (i - st.peekLast() - 1));
            }
            st.offerLast(i);
        }
        return ans;
    }
}
