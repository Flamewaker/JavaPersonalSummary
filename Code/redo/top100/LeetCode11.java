package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/7 7:46
 * @description: 11. 盛最多水的容器
 */
public class LeetCode11 {
    public int maxArea(int[] height) {
        if (height.length < 2) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int ans = 0;
        while (left < right) {
            int width = right - left;
            int area = height[left] < height[right] ? height[left++] * width : height[right--] * width;
            ans = Math.max(ans, area);
        }
        return ans;
    }
}
