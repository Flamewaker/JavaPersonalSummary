package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description 字符串最后一个单词的长度
 * @date 10:15 AM 2022/7/2
 */
public class HJ1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            System.out.println(lengthOfLast(str));
        }
    }

    public static int lengthOfLast(String str) {
        String[] s = str.split(" ");
        return s[s.length - 1].length();

    }
}
