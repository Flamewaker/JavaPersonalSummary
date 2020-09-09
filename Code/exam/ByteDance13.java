package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/22 21:06
 * @description: 求1~n中，每个数的二进制中1的总个数
 * 把一个整数n减去1，再和原来的整数与运算，会把该整数的最右边的1变成0，
 * 补充：一个整数的二进制中有多少个1，就可以进行多少次这样的操作。循环结束的条件是n为0(判断二进制中1的个数)
 * 那么：利用 n&(n-1) 这个操作可以去掉末尾的1。利用递推f(n) = 1+f(n&(n-1))。这样就是利用空间来换时间，时间复杂度为O(n)。
 */

public class ByteDance13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int f[] = new int[n + 1];
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            f[i] = 1 + f[i & (i - 1)];
            ans += f[i];
        }
        System.out.println(ans);
    }


}
