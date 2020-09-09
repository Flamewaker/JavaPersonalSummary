package com.todd.redo.top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/7 9:13
 * @description: 15. 三数之和
 */
public class LeetCode15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return ans;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < nums.length - 2; i++) {
            int temp = nums[i];
            if (temp > 0) {
                return ans;
            }
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = temp + nums[start] + nums[end];
                if (sum == 0) {
                    ans.add(Arrays.asList(temp, nums[start], nums[end]));
                    while (start < end && nums[start + 1] == nums[start]) {
                        start++;
                    }
                    while (start < end && nums[end - 1] == nums[end]) {
                        end--;
                    }
                    start++;
                    end--;
                } else if (sum > 0) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        return ans;
    }
}
