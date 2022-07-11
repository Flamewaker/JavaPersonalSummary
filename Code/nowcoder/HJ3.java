package com.todd.nowcoder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author tongchengdong
 * @description 明明的随机数
 * @date 5:30 PM 2022/7/2
 */
public class HJ3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            List<Integer> ans = solve(nums);
            for (Integer num : ans) {
                System.out.println(num);
            }
        }
    }

    public static List<Integer> solve(int[] nums) {
        boolean[] vis = new boolean[500];
        for (int i = 0; i < nums.length; i++) {
            vis[nums[i]] = true;
        }
        List<Integer> ans = new LinkedList<>();
        for (int i = 0; i < vis.length; i++) {
            if (vis[i] == true) {
                ans.add(i);
            }
        }
        return ans;
    }
}
