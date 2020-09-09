package com.todd.exam;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/15 15:46
 * @description: TODO
 */
public class Meituan_8_15_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        ArrayList<long[]> ans = new ArrayList<>();
        for (long i = 1; i <= n; i++) {
            long a = i;
            long b = 4 * i;
            if (judge(a, b)) {
                ans.add(new long[]{a, b});
            }
        }
        if (n <= 0) {
            System.out.println(0);
        } else {
            System.out.println(ans.size());
            for (int i = 0; i < ans.size(); i++) {
                System.out.println(ans.get(i)[0] + " " + ans.get(i)[1]);
            }
        }

    }

    public static boolean judge(long a, long b) {
        long temp1 = 0;
        long t = a;
        while (t != 0) {
            temp1 = temp1 * 10 + t % 10;
            t /= 10;
        }
        long temp2 = 0;
        long k = b;
        while (k != 0) {
            temp2 = temp2 * 10 + k % 10;
            k /= 10;
        }
        return temp1 == b || temp2 == a;
    }
}
