package com.todd.code;

/**
 * @author todd
 * @date 2020/6/26 14:43
 * @description: 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
 * 1. 暴力循环，时间复杂度O(n2)
 * 2. 哈希表，时间复杂度O(n),空间复杂度O(n)
 * 3. 二分查找，时间复杂度O(nlogn),空间复杂度O(1)，二分法可以用于确定一个有范围的整数
 * 二分法的思路是先猜一个数（有效范围 [left, right]里的中间数 mid），然后统计原始数组中小于等于这个中间数的元素的个数 cnt，如果 cnt 大于 mid，重复元素就可能在区间 [left, mid) 里, 若不在则为mid；
 * 否则在[mid + 1, right] 最终 l == r 要找右区间的第一个符合的数。
 */
public class LeetCode287 {
    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int l = 1, r = n;
        while (l < r) {
            int mid = (l + r) >> 1;
            int cnt = 0;
            for (int i = 0; i < n; ++i) {
                if (nums[i] <= mid) {
                    cnt++;
                }
            }
            if (cnt > mid) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;

    }
}
