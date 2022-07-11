package com.todd.nowcoder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ14 字符串排序
 * @date 9:48 PM 2022/7/2
 */
public class HJ14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            String[] arr = new String[n];
            for (int i = 0; i < n; i++) {
                String str = sc.next();
                arr[i] = str;
            }
            Arrays.sort(arr);
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }
        }
    }
}
