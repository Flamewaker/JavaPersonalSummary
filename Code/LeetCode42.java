package com.todd;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 单调栈
 *
 * @Author todd
 * @Date 2020/4/4
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
}
