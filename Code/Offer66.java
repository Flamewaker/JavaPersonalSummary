package com.todd;

/**
 * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B 中的元素 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
 * 思路：分别迭代计算下三角和上三角两部分的乘积，即可不使用除法就获得结果。
 * @Auther todd
 * @Date 2020/5/15
 */
public class Offer66 {
    public int[] constructArr(int[] a) {
        if(a == null || a.length == 0) {
            return new int[0];
        }
        int len = a.length;
        int[] b = new int[len];
        int[] c = new int[len];
        int[] d = new int[len];
        c[0] = 1;
        d[len - 1] = 1;
        for (int i = 1; i < len; i++) {
            c[i] = a[i - 1] * c[i - 1];
            d[len - i - 1] = a[len - i] * d[len - i];
        }
        for (int i = 0; i < len; i++) {
            b[i] = c[i] * d[i];
        }
        return b;
    }

    public int[] constructArr2(int[] a) {
        if(a.length == 0) return new int[0];
        int[] b = new int[a.length];
        b[0] = 1;
        int tmp = 1;
        for(int i = 1; i < a.length; i++) {
            b[i] = b[i - 1] * a[i - 1];
        }
        for(int i = a.length - 2; i >= 0; i--) {
            tmp *= a[i + 1];
            b[i] *= tmp;
        }
        return b;
    }

}
