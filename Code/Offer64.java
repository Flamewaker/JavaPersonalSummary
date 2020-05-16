package com.todd;

/**
 * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer64 {
    public int sumNums(int n) {
        return (1 + n) * n / 2;
    }

    public int sumNums2(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += i;
        }
        return res;
    }

    public int sumNums3(int n) {
        if (n == 1) {
            return 1;
        }
        n += sumNums3(n - 1);
        return n;
    }

    //短路效应实现
    public int sumNums4(int n) {
        boolean x = n > 1 && (n += sumNums(n - 1)) > 0;
        return n;
    }

}
