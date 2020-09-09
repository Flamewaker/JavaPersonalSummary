package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 11:53
 * @description: 剑指 Offer 11. 旋转数组的最小数字
 */
public class Offer11 {
    public int minArray(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (numbers[mid] > numbers[right]) {
                left = mid + 1;
            } else if (numbers[mid] < numbers[right]) {
                right = mid;
            } else {
                right--;
            }
        }
        return numbers[left];
    }
}
