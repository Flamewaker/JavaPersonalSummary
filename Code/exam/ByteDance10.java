package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/26 11:45
 * @description: 万万没想到之聪明的编辑
 * 状态机
 */
public class ByteDance10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String s = sc.next();
            StringBuilder str = new StringBuilder(s);
            int status = 0;
            int j = 0;
            while (j < str.length()) {
                if (status == 0) {
                    status++;
                } else if (status == 1) {
                    if (j >= 1 && str.charAt(j) == str.charAt(j - 1)) {
                        status++;
                    }
                } else if (status == 2) {
                    if (j >= 2 && str.charAt(j) != str.charAt(j - 1)) {
                        status++;
                    } else {
                        str.deleteCharAt(j);
                        continue;
                    }
                } else {
                    if (j >= 3 && str.charAt(j) == str.charAt(j - 1)) {
                        str.deleteCharAt(j);
                        continue;
                    } else {
                        status = 1;
                    }
                }
                j++;
            }
            System.out.println(str.toString());
        }
    }
}
