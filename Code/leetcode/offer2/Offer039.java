package com.todd.leetcode.offer2;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 039. 直方图最大矩形面积
 * @date 4:10 PM 2022/6/17
 */
public class Offer039 {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if(len == 0){
            return 0;
        }
        if(len == 1){
            return heights[0];
        }
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, heights.length);
        newHeights[len + 1] = 0;
        int ans = 0;
        Deque<Integer> st = new LinkedList<>();
        for(int i = 0; i < len + 2; i++){
            while(!st.isEmpty() && newHeights[i] < newHeights[st.peekLast()]){
                int cur = st.pollLast();
                while(!st.isEmpty() && newHeights[st.peekLast()] == newHeights[cur]){
                    st.pollLast();
                }
                ans = Math.max(ans, newHeights[cur] * (i - st.peekLast() - 1));
            }
            st.offerLast(i);
        }
        return ans;
    }
}
