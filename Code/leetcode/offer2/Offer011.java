package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 011. 0 和 1 个数相同的子数组
 * 整体思路：
 * 1. 滑动窗口
 * 2. 维护0和1的个数
 * @date 6:37 PM 2022/6/14
 */
public class Offer011 {
    public int findMaxLength(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int maxLength = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int counter = 0;
        map.put(counter, -1);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num == 1) {
                counter++;
            } else {
                counter--;
            }
            if (map.containsKey(counter)) {
                int prevIndex = map.get(counter);
                maxLength = Math.max(maxLength, i - prevIndex);
            } else {
                map.put(counter, i);
            }
        }
        return maxLength;
    }
}
