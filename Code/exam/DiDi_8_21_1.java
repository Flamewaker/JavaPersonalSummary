package com.todd.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/21 19:47
 * @description: 暴力枚举
 */
public class DiDi_8_21_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<int[]> ans = new ArrayList<>();
        for (int a = 1; a <= 9; a++) {
            for (int b = 0; b <= 9 ; b++) {
                if (b == a) {
                    continue;
                }
                for (int c = 0; c <= 9; c++) {
                    if (c == a || c == b) {
                        continue;
                    }
                    int x = a * 100 + b * 10 + c;
                    int y = a * 100 + c * 10 + c;
                    if (x + y == n) {
                        ans.add(new int[]{x, y});
                    }
                }
            }
        }
        System.out.println(ans.size());
        for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i)[0] + " " + ans.get(i)[1]);
        }
    }


}
