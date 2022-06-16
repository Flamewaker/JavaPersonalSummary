package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/8/9 17:52
 * @description: 213. 打家劫舍 II
 */
public class LeetCode213 {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int len = nums.length;
        if(len == 1) return nums[0];
        else if(len == 2) return Math.max(nums[0], nums[1]);
        int pre1 = 0, cur1 = nums[0], temp1;
        int pre2 = 0, cur2 = nums[1], temp2;
        for(int i = 2; i < len; i++){
            temp1 = cur1;
            temp2 = cur2;
            cur1 = Math.max(pre1 + nums[i - 1], cur1);
            cur2 = Math.max(pre2 + nums[i], cur2);
            pre1 = temp1;
            pre2 = temp2;
        }
        return Math.max(cur1, cur2);
    }
}
