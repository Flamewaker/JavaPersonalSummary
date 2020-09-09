package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 13:02
 * @description: Z国的货币系统包含面值1元、4元、16元、64元共计4种硬币，以及面值1024元的纸币。现在小Y使用1024元的纸币购买了一件价值为N (0 < N \le 1024)N(0<N≤1024)的商品，请问最少他会收到多少硬币？
 * 贪心算法，简单题。
 */
public class ByteDance04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int N = sc.nextInt();
            int m = 1024 - N;
            int ans = 0;
            int[] coin = {64, 16, 4, 1};
            for (int i = 0; i < coin.length; i++) {
                if (coin[i] <= m) {
                    int curCoinNum = m / coin[i];
                    m -= curCoinNum * coin[i];
                    ans += curCoinNum;
                }
            }
            System.out.println(ans);
        }

    }
}
