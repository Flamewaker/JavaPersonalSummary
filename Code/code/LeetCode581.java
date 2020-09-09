package com.todd.code;

/**
 * @author todd
 * @date 2020/6/17 9:06
 * @description: 给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 你找到的子数组应是最短的，请输出它的长度。
 * 1. 初步思路，顺序遍历，对每个位置找到当前位置的对应数字，如果对应数字不符合，则记录位置。找到左右两个坐标。O(n2)
 * 2. 排序，找到对应的不符合的坐标。O(nlogn)
 * 3. 第二个思路，找到逆序位置，找到逆序位置，后一个数的最小值，前一个数的最大值。然后判断两个数应该放置的位置，输出。O(n)
 * 4. 指针i指向从头开始遍历，指针j从尾开始遍历。分别找到最后逆序的位置，
 * 指针i在此过程中，如果有比遍历过程中最大的数更小的数，则说明存在逆序，此时更新标志位，一直到遍历完毕。这样就能记录下其边界。反之对于指针j，采用同样的过程，如果有比遍历过程中最小的数更大的数，则说明存在逆序，此时更新标志位，一直到遍历完毕。
 * 从左到右找出最后一个破坏递增的数, 从右到左找出最后一个破坏递减的数.
 */
public class LeetCode581 {
    public static void main(String[] args) {
        System.out.println(findUnsortedSubarray2(new int[]{2, 6, 4, 8, 10, 9, 15}));
    }

    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = nums.length - 1;
        int right = 0;
        int minIndex = -1;
        int maxIndex = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                minIndex = minIndex == -1 ? i : nums[i] <= nums[minIndex] ? i : minIndex;
                maxIndex = maxIndex == -1 ? i - 1 : nums[i - 1] >= nums[maxIndex] ? i - 1 : maxIndex;
            }
        }
        if (maxIndex == -1 && minIndex == -1) {
            return 0;
        }
        for (int i = 0; i < nums.length; i++) {
            if (i < minIndex && nums[i] > nums[minIndex]) {
                left = Math.min(i, left);
            }
            if (i > maxIndex && nums[i] < nums[maxIndex]) {
                right = Math.max(i, right);
            }
        }
        return right - left + 1;
    }

    public static int findUnsortedSubarray2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int high = 0, low = len - 1, curMax = Integer.MIN_VALUE, curMin = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            if (nums[i] >= curMax) {
                curMax = nums[i];
            } else {
                high = i;
            }
            if (nums[len - i - 1] <= curMin) {
                curMin = nums[len - i - 1];
            } else {
                low = len - i - 1;
            }
        }
        return high > low ? high - low + 1 : 0;
    }
}
