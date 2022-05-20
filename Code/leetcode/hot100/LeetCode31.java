package com.todd.leetcode.hot100;

/**
 * @author tongchengdong
 * @description LeetCode31. 下一个排列
 *  整体思路：
 *  1. 下一个数比当前数大：下一个排列通过后面的大数和前面的小数进行交换
 *  2. 数字增长幅度尽可能小： 在尽可能靠右的低位进行交换，需要从后向前查找。交换数字后需要对后面的数据进行升序排列, 来保证是最小的下一个排列。
 */
public class LeetCode31 {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int len = nums.length;
        // 1. 从后往前找第一个相邻的升序对[i，j]，找不到就直接将数组逆置
        for (int i = len - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                // 2. [j, end]找第一个比[i]大的数字[k]
                int k = i + 1;
                for (int j = i + 1; j < len; j++) {
                    if (nums[i] < nums[j]) {
                        k = j;
                    }
                }
                // 3. 交换[j]和[k]的顺序
                swap(nums, i, k);
                // 4. 将[j, end]数字升序排列, 可替换 Arrays.sort(nums, i + 1, len)
                reverse(nums, i + 1, len - 1);
                return;
            }
        }
        reverse(nums, 0, len - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }
}
