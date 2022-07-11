package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ4 字符串分隔
 * @date 8:31 PM 2022/7/2
 */
public class HJ4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            int size = str.length();
            int addZero = 8 - size % 8;
            while ((addZero > 0) && (addZero < 8)) {
                sb.append("0");
                addZero--;
            }
            String str1 = sb.toString();
            int cnt = str1.length() / 8;
            int i = 0;
            while (i < cnt) {
                System.out.println(str1.substring(i * 8, (i + 1) * 8));
                i++;
            }
        }
    }
}
