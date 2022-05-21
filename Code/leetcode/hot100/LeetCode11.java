package com.todd.leetcode.hot100;

/**
 * @author tongchengdong
 * @description 11. 盛最多水的容器
 * 整体思路：
 * 贪心算法
 * 1. 水量计算公式(j - i) * Math.min(heigth[i], height[j])
 * 2. 简单做法：双指针遍历，nlogn
 * 3. 优化做法：双指针遍历，定义left和right，水的容量是由较小的那个板决定的，为了提升水的容量，因此需要提升短板的高度
 * @date 11:58 PM 2022/5/20
 */
public class LeetCode11 {
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
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
