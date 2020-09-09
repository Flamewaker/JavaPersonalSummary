package com.todd.exam;

import java.util.Scanner;


/**
 * 一个只包含0和1的数组，反转k个0之后最长的连续1
 */
public class bilibili_9_4_01 {
    public int GetMaxConsecutiveOnes (int[] arr, int k) {
        int a = 0;
        int ans = 0;
        for (int right = 0, left = 0; right < arr.length; right++) {
            if (arr[right] == 0) {
                a++;
            }
            while (a > k) {
                if (arr[left++] == 0) {
                    a--;
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
