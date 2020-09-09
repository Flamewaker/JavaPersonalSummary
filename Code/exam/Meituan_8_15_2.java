package com.todd.exam;

import java.util.HashSet;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/15 15:46
 * @description: TODO
 */
public class Meituan_8_15_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean flag = true;
        String start = "";
        int count = 0;
        HashSet<String> last = new HashSet<>();
        for (int i = 0; i < n; i++) {
            String a = sc.next();
            String b = sc.next();
            last.add(a);
            if (flag) {
                if (a.equals(b)) {
                    count++;
                } else {
                    start = a;
                    flag = false;
                }
            } else if (b.equals(start)) {
                start = "";
                flag = true;
                count++;
            }
        }
        if (flag == false) {
            count++;
        }
        System.out.println(count);
    }
}
