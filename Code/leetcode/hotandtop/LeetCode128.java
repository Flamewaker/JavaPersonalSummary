package com.todd.leetcode.hotandtop;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author tongchengdong
 * @description 128. 最长连续序列 (***)
 * 整体思路：
 * 1. 第一种思路：给数组排序，判断最长连续序列的长度。O(nlogn) + O(n)
 * 2. 第二种思路：遍历一遍数组，将所有数据加入HashMap<Integer, Boolean>中。然后再遍历一遍数组，对未访问过的数据，遍历其最长连续序列。
 * 时间复杂度： O(n) + O(n) HashSet查询为O(1)
 * 3. 第二种思路优化：遍历一遍数组，将所有数据加入Set<Integer>中，遍历数据后移除数据。
 * @date 5:05 PM 2022/5/22
 */
public class LeetCode128 {
    /**
     * 第一种思路
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;
        int index = 0;
        while (index < n) {
            // 注意排除相等情况
            int count = 1;
            while (index + 1 < n && (nums[index + 1] == nums[index] + 1 || nums[index + 1] == nums[index])) {
                if (nums[index + 1] == nums[index] + 1) {
                    count++;
                }
                index++;
            }
            ans = Math.max(ans, count);
            index++;
        }
        return ans;
    }

    /**
     * 第二种思路
     */
    public int longestConsecutiveSecond(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int ans = 0;
        Map<Integer, Boolean> map = new HashMap();
        for (int i = 0; i < n; i++) {
            map.put(nums[i], false);
        }
        for (int i = 0; i < n; i++) {
            if (Boolean.TRUE.equals(map.get(nums[i]))) {
                continue;
            }
            map.put(nums[i], true);
            int left = nums[i] - 1;
            int right = nums[i] + 1;
            while (map.containsKey(left)) {
                map.put(left, true);
                left--;
            }
            while (map.containsKey(right)) {
                map.put(right, true);
                right++;
            }

            ans = Math.max(ans, right - left - 1);
        }
        return ans;
    }

    /**
     * 第二种思路优化方案
     */
    public int longestConsecutiveSecondOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        int ans = 0;
        for (int num : nums) {
            set.add(num);
        }
        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
                int left = num - 1;
                int right = num + 1;

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
