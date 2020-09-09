package com.todd.code;

/**
 * @author todd
 * @date 2020/5/18 9:43
 * @description: 乘积最大子数组
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * 类似题目 LeetCode 53, 300
 * 可以得到：当前位置的最优解未必是由前一个位置的最优解转移得到的。所以不能直接用上一个最优解直接得到。（LeetCode 53）
 * 令imax为当前最大值，则当前最大值为 imax = max(imax * nums[i], imin * nums[i], nums[i])
 * 由于存在负数，那么会导致最大的变最小的，最小的变最大的。因此还需要维护当前最小值imin，imin = min(imax * nums[i], imin * nums[i], nums[i])
 * 如果当前值为正，希望前一个位置结尾的某个段的乘积为正，才能得到最大值。如果当前值为负，希望前一个位置结尾的某个段的乘积为负，才能得到最大值。当前位置为0， 全为0；
 */
public class LeetCode152 {
    public int maxProduct(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int ans = nums[0];
        int iMax = nums[0];
        int iMin = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int mx = iMax, mn = iMin;
            iMax = Math.max(nums[i], Math.max(nums[i] * mx, nums[i] * mn));
            iMin = Math.min(nums[i], Math.min(nums[i] * mx, nums[i] * mn));
            ans = Math.max(ans, iMax);
        }
        return ans;
    }

    /**
     * 当 nums[i] > 0 时，由于是乘积关系：
     * 最大值乘以正数依然是最大值；
     * 最小值乘以同一个正数依然是最小值；
     * 当 nums[i] < 0 时，依然是由于乘积关系：
     * 最大值乘以负数变成了最小值；
     * 最小值乘以同一个负数变成最大值；
     * 当 nums[i] = 0 的时候，由于 nums[i] 必须被选取，最大值和最小值都变成 0 ，合并到上面任意一种情况均成立。
     * dp[len][2]，二维状态，表示当前最大值和最小值。
     * 由于当前值的状态只和前一个状态相关，因此可以对空间进行优化，每次保留前一个状态即可。
     */

}
