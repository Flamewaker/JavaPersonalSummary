package com.todd.redo.top100;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author todd
 * @date 2020/8/24 19:42
 * @description: 128. 最长连续序列
 */
public class LeetCode128 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 0;
        int ans = 0;
        int i = 0;
        while (i < nums.length) {
            count = 1;
            while (i < nums.length - 1 && (nums[i] == nums[i + 1] || nums[i] + 1 == nums[i + 1])) {
                if (nums[i] != nums[i + 1]) {
                    count++;
                }
                i++;
            }
            ans  = Math.max(ans, count);
            i++;
        }
        return ans;
    }

    public int longestConsecutive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                int cur = nums[i];
                set.remove(cur);
                int left = cur - 1;
                int right = cur + 1;
                while (set.contains(left)) {
                    set.remove(left);
                    left--;
                }
                while (set.contains(right)) {
                    set.remove(right);
                    right++;
                }
                ans = Math.max(ans, right - left - 1);
            }

        }
        return ans;
    }
}
