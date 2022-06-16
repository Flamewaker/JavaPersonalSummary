package com.todd.leetcode.offer1;

/**
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
 * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 * 思路： 排序数组中的搜索问题，首先想到 二分法 解决. 找到第一个不对应数字的下标。注意如果最后没有找到直接返回left即可。
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer53_2 {
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int ans = 0;
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] != mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 变形：找出数组中数值和下标相等的元素。（元素唯一）
     * 思路：使用二分法，由于当数值大于下标的时候，其后的数值一定大于其下标。
     * 当数值小于下标的时候，其后的数值一定小于其下标。
     * 因此利用二分法使用如下代码：
     * if(nums[mid] == mid) return mid;
     * else if(nums[mid] > mid) right = mid - 1;
     * else left = mid + 1;
     */
}
