package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 14:33
 * @description: 剑指 Offer 53 - II. 0～n-1中缺失的数字
 */
public class Offer53_2 {
    public int missingNumber(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] != mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public int missingNumber2(int[] nums) {
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i] ^ i;
        }
        return res;
    }
    public int missingNumber3(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += i - nums[i];
        }
        res = nums.length + res;
        return res;
    }
}
