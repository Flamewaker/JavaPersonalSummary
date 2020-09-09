package com.todd.exam;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/26 12:28
 * @description: 水仙花数
 * 春天是鲜花的季节，水仙花就是其中最迷人的代表，数学上有个水仙花数，他是这样定义的：“水仙花数”是指一个三位数，它的各位数字的立方和等于其本身，比如：153=1^3+5^3+3^3。 现在要求输出所有在m和n范围内的水仙花数。
 * 暴力搜索。
 */
public class ByteDance11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            ArrayList<Integer> ans = new ArrayList<>();
            for (int i = m; i <= n; i++) {
                int temp = i;
                int sum = 0;
                while (temp != 0) {
                    int x = temp % 10;
                    sum += Math.pow(x, 3);
                    temp /= 10;
                }
                if (sum == i) {
                    ans.add(sum);
                }
            }
            if (ans.size() == 0) {
                System.out.println("no");
            } else {
                for (int i = 0; i < ans.size(); i++) {
                    if(i != ans.size() - 1) {
                        System.out.print(ans.get(i) + " ");
                    } else {
                        System.out.println(ans.get(i));
                    }
                }
            }
        }
    }
}
