package com.todd.exam;

import java.util.Arrays;
import java.util.Scanner;

public class Huawei_8_19_2 {

    public static int ans = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Arrays.sort(nums);
        int index = 0;
        int last = 1;
        int level = 0;
        boolean flag = false;
        while (index < n) {
            if (level != nums[index]) {
                flag = true;
                break;
            }

            int count = 1;
            while (index < n - 1 && nums[index + 1] == level) {
                count++;
                index++;
            }
            if (level == 0) {
                if (count > 1) {
                    flag = true;
                    break;
                }
                ans = 1;
                last = 1;
            } else {
                if (count > 2 * last) {
                    flag = true;
                    break;
                }
                ans = ans * getC(2 * last, count) % 1000000007;
                last = count;
            }
            index++;
            level++;
        }
        if (flag) {
            System.out.println(0);
        } else {
            System.out.println(ans);
        }

    }

    public static int getC(int m, int n) {
        int a = 1;
        int b = 1;
        for (int i = 1; i <= n; i++) {
            a *= i;
        }
        for (int i = m; i > m - n; i--) {
            b *= i;
        }
        return b / a % 1000000007;
    }
}
