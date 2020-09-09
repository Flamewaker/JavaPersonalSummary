package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/26 13:41
 * @description: 栈
 * 定一个数组序列,需要求选出一个区间,使得该区间是所有区间中经过如下计算的值最大的一个：
 * 区间中的最小数*区间所有数的和最后程序输出经过计算后的最大值即可，不需要输出具体的区间。如给定序列 [6 2 1]则根据上述公式,可得到所有可以选定各个区间的计算值:
 * 对于某个数，直接暴力拓展区间，维护区间最小值为当前数。
 */
public class ByteDance12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }

            int index = 0;
            int ans = 0;
            while (index < n) {
                int left = index;
                int right = index + 1;
                int sum = 0;
                while (left >= 0 && nums[left] >= nums[index]) {
                    sum += nums[left];
                    left--;
                }
                while (right < n && nums[right] >= nums[index]) {
                    sum += nums[right];
                    right++;
                }
                ans = Math.max(ans, sum * nums[index]);
                index++;
                while (index < n && nums[index] == nums[index - 1]) {
                    index++;
                }
            }
            System.out.println(ans);
        }
    }
}
