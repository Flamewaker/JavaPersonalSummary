package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description 计算某字符出现次数
 * @date 5:19 PM 2022/7/2
 */
public class HJ2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            String s = sc.nextLine();
            System.out.println(countCharacter(str.toUpperCase(), s.toUpperCase()));
        }
    }

    public static int countCharacter(String str, String s) {
        char a = s.charAt(0);
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (a == str.charAt(i)) {
                cnt++;
            }
        }
        return cnt;
    }
}
