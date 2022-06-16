package com.todd.leetcode.hotandtop;

/**
 * @author todd
 * @date 2020/8/6 15:46
 * @description: TODO
 */
public class LeetCode4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums1.length;
        int length = n + m;
        int mid1 = 0, mid2 = 0;
        for (int k = 0, i = 0, j = 0; k <= length / 2; k++) {
            mid1 = mid2;
            if (i == nums1.length) {
                mid2 = nums2[j++];
            } else if (j == nums2.length) {
                mid2 = nums1[j++];
            } else if (nums1[i] < nums2[j]) {
                mid2 = nums1[i++];
            } else {
                mid2 = nums2[j++];
            }
        }
        return length % 2 == 1 ? mid2 : (mid1 + mid2) / 2.0;
    }
}
