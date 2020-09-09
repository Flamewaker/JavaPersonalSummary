package com.todd.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/22 18:42
 * @description: Redraiment的走法
 * Redraiment是走梅花桩的高手。Redraiment总是起点不限，从前到后，往高的桩子走，但走的步数最多，不知道为什么？你能替Redraiment研究他最多走的步数吗？
 */
public class HuaWei4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            ArrayList<Integer> ans = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            int flag =  GetResult(n, nums, ans);
            if (flag == 1) {
                System.out.println(ans.get(0));
            }
        }
    }

    private static int GetResult(int num, int[] pInput, ArrayList<Integer> pResult) {
        if (num == 0) {
            return 0;
        }
        int[] dp = new int[num];
        int ans = 0;
        for (int i = 0; i < num; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (pInput[i] > pInput[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    ans = Math.max(ans, dp[i]);
                }
            }
        }
        pResult.add(ans);
        return 1;
    }
}
