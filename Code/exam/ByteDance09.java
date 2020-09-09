package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/26 11:14
 * @description: 万万没想到之抓捕孔连顺
 * 定义一个变量j，用来判断当前i位置到j位置的距离D的情况，开始的初始值为0，代表从第一个位置开始判断，如果最大值pos（i）-pos（j）> d,说明j之前的所有位置到当前 i 位置的距离都>d.
 * 如果最大值pos（i）-pos（j）< d，就满足最远的距离不超过D，j表示当前位置i到第一次出现距离小于D的位置，也就是j之前的位置到当前位置的距离都大于D，这些要剔除掉，j位置是第一个满足题意的位置。所以最终是i - j个满足题意的距离中选2个位置.
 * 注意使用滑动窗口进行优化。
 */
public class ByteDance09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int d = sc.nextInt();
            int[] buildings = new int[n];
            for (int i = 0; i < n; i++) {
                buildings[i] = sc.nextInt();
            }
            long ans = 0;
            int right = 2;
            int left = 0;
            while (right < n) {
                while (buildings[right] - buildings[left] > d) {
                    left++;
                }
                ans += compute(right - left);
                right++;
            }

            System.out.println(ans % 99997867);
        }
    }

    //注意long单位

    public static long compute(long n) {
        return n * (n - 1) / 2;
    }
}
