package com.todd;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 通过17%
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Meituan512_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        HashSet<Integer> destroyedShelter = new HashSet<>();
        int last = n;
        for (int i = 0; i < m; i++) {
            int callNum = scanner.nextInt();
            int p = scanner.nextInt();
            if (callNum == 1) {
                if (p > n || p < 1 || destroyedShelter.contains(p)) {
                    continue;
                }
                destroyedShelter.add(p);
            } else if (callNum == 2) {
                if (p >= last) {
                    System.out.println(-1);
                    continue;
                }
                boolean flag = false;
                for (int j = p; j <= n; j++) {
                    if (!destroyedShelter.contains(j)) {
                        flag = true;
                        System.out.println(j);
                    }
                }
                if (!flag) {
                    last = Math.min(last, p);
                    System.out.println(-1);
                }

            }
        }

    }
}
