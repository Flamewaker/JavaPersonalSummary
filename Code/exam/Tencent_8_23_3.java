package com.todd.exam;
import java.util.*;

/**
 * @author todd
 * @date 2020/8/23 19:25
 * @description: 给一个数 n 为有多少组测试样例 AC
 * 给出一个数m 其可以由两个数组成，
 * m = a + b
 * 求 a 的每一位 + b的每一位 的最大价值。
 */
public class Tencent_8_23_3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i = 0; i < n; i++) {
            long m = sc.nextLong();
            System.out.println(solve(m));
        }

    }

    public static long solve(long m) {
        if (m <= 9) {
            return m;
        } else {
            long count = 1;
            long temp = m;
            while (temp / 10 != 0) {
                temp /= 10;
                count *= 10;
            }
            long a = count - 1;
            long b = m - a;

            long ans = 0;

            while (a != 0) {
                ans += a % 10;
                a /= 10;
            }
            while (b != 0) {
                ans += b % 10;
                b /= 10;
            }
            return ans;
        }


    }
}