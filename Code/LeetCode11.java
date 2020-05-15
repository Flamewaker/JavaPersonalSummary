package com.todd;

/**
 * 给你n个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i, ai)。
 * 在坐标内画n条垂直线，垂直线i的两个端点分别为(i,ai)和(i,0)。
 * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
 * 1. 双指针 + 状态剪枝
 * 在每一个状态下，无论长板或短板收窄1格，都会导致水槽 底边宽度-1。
 * 若向内移动短板，水槽的短板 min(h[i], h[j]) 可能变大，因此水槽面积 S(i,j)可能增大。
 * 若向内移动长板，水槽的短板 min(h[i], h[j]) 不变或变小，下个水槽的面积一定小于当前水槽面积。
 * 因此，向内收窄短板可以获取面积最大值。因此，不需要枚举所有的状态，使用双指针剔除不需要的状态即可。
 *
 * @Auther todd
 * @Date 2020/4/18
 */
public class LeetCode11 {
    public int maxArea(int[] height) {
        if (height.length < 2) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int res = 0;
        while (left < right) {
            res = height[left] < height[right] ?
                    Math.max(res, (right - left) * height[left++]) :
                    Math.max(res, (right - left) * height[right--]);
        }
        return res;
    }
}
