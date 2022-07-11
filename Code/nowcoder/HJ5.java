package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ5 进制转换
 * @date 8:46 PM 2022/7/2
 */
public class HJ5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();

            int count = 0;

            for (int i = 0; i < s.length() - 2; i++) {
                //由于前面两位是'0x'，故从第三位开始
                char tc = s.charAt(i + 2);
                int t = 0;

                if (tc >= '0' && tc <= '9') {
                    t = tc - '0';
                } else if (tc >= 'A' && tc <= 'F') {
                    t = tc - 'A' + 10;
                } else if (tc >= 'a' && tc <= 'f') {
                    t = tc - 'a' + 10;
                }
                count += t * Math.pow(16, s.length() - i - 3);
            }
            System.out.println(count);
        }
    }
}
