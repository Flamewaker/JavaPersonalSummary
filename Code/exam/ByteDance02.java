package com.todd.exam;

import java.util.*;

/**
 * @author todd
 * @date 2020/6/25 10:41
 * @description: 头条校招
 * 一场考试包含3道开放性题目，假设他们的难度从小到大分别为a,b,c，我们希望这3道题能满足下列条件：
 * a<=b<=c
 * b-a<=10
 * c-b<=10
 * 所有出题人一共出了n道开放性题目。现在我们想把这n道题分布到若干场考试中，计算出我们最少还需要再出几道题吗？
 *
 * 滑动窗口O(n) 3个一组分4种情况 先从小到大排个序 O(nlogn)
 * 下标永远指向第一个数
 * 第1种： 这三个都满足要求 下标直接+3
 * 第2种： 第二个比第一个大超过20 那么第一个后面添加2个数 下标+1
 * 第3种：第二个比第一个大超过10 但不大于20  中间加1个即可 下标+2
 * 第4种：第一第二满足 第三个第二个不满足 不管怎样 第二个后面添加1个 下标+2
 * 第3第4可以合并 但是原理不一样
 */
public class ByteDance02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            System.out.println(solve(nums));
        }
    }
    public static int solve(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        int count = 0;
        int left = nums[0];
        int right = nums[0] + 20;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                left = nums[i];
                right = nums[i] + 20;
                count++;
            } else if (count == 1) {
                if (nums[i] - left <= 10) {
                    count++;
                }
                else if (nums[i] <= right) {
                    count = 0;
                    ans ++;
                } else {
                    count = 0;
                    ans += 2;
                }
            } else {
                if (nums[i] > right){
                    ans++;
                }
                count = 0;
            }
        }
        if (count != 0) {
            ans += 3 - count;
        }
        return ans;
    }
}
