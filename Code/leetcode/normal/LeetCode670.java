package com.todd.leetcode.normal;

import java.util.Arrays;

/**
 * @author todd
 * @date 2020/7/19 9:29
 * @description: 670. 最大交换
 * 7.19字节跳动提前批笔试原题，当时写的有点问题，现在过来做一做
 * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
 * 1. 首先我们将大数字拆分成一个一个的一位数;
 * 2. 然后遍历数组, 只有遇到第一次递增的时候才会触发置换条件，记录当前坐标作为分割点。如果遍历到最后都没有递增，则直接返回;
 * 3. 记录右边的最后的最大值的位置i，记录左边的第一次小于最大值的下标j
 * 4. 交换两个元素，即可得到最大交换。
 */
public class LeetCode670 {
    public int maximumSwap(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        int length = chars.length;
        int index = 0;
        for (int i = 1; i < length; i++) {
            if (i > 0 && chars[i] > chars[i - 1]) {
                index = i;
                break;
            }

            if (i == length - 1) {
                return num;
            }
        }

        int indexRight = index;
        for (int i = index; i < length; i++) {
            if (chars[i] >= chars[indexRight]) {
                indexRight = i;
            }
        }

        int indexLeft = 0;
        for (int i = 0; i < index; i++) {
            if (chars[i] < chars[indexRight]) {
                indexLeft = i;
                break;
            }
        }
        swap(indexLeft, indexRight, chars);
        return Integer.parseInt(new String(chars));
    }


    public static void swap(int i, int j, char[] num) {
        char temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

}
