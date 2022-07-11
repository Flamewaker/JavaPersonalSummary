package com.todd.nowcoder;

import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ7 取近似值
 * 当float转换为int的时候会丢失小数点之后的数
 * @date 9:25 PM 2022/7/2
 */
public class HJ7 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double number = in.nextDouble();
        System.out.println((int) (number + 0.5));
    }
}
