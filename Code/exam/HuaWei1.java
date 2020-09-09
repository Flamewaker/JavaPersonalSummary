package com.todd.exam;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/22 16:26
 * @description: 计算一个数字的立方根，不使用库函数
 */
public class HuaWei1 {

    public static double getCubeRoot(double input) {
        double min = 0;
        double max = input;
        double mid = 0;

        // 注意，这里的精度要提高一点，否则某些测试用例无法通过
        while ((max - min) > 0.001) {
            mid = (max + min) / 2;
            if (mid * mid * mid > input) {
                max = mid;
            }
            else if (mid * mid * mid < input) {
                min = mid;
            }
            else {
                return mid;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            double input = sc.nextDouble();
            double result = getCubeRoot(input);
            System.out.printf("%.1f\n", result);
        }
        sc.close();
    }
}
