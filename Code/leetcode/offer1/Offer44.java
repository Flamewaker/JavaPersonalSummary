package com.todd.leetcode.offer1;

/**
 * 数字以0123456789101112131415…的格式序列化到一个字符序列中。在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
 * 请写一个函数，求任意第n位对应的数字。
 * 找规律 9， 90 * 2， 900 * 3 。。。。。需要知道的是(res - 1) / count对应第几个数（从0开始）。
 *
 * @Author todd
 * @Date 2020/4/23
 */
public class Offer44 {
    public static int findNthDigit(int n) {
        if (n == 0) {
            return 0;
        }
        int count = 1;
        int res = n;
        while (res > 9 * Math.pow(10, count - 1) * count) {
            res -= 9 * Math.pow(10, count - 1) * count;
            count++;
        }
        int num = (res - 1) / count;
        int index = count - (res - num * count);
        int baseNum = (int) Math.pow(10, count - 1);
        int curNum = baseNum + num;
        while (index-- > 0) {
            curNum /= 10;
        }
        return curNum % 10;

//        char[] result = String.valueOf((int) Math.pow(10,count-1) + (res - 1) / count).toCharArray();//我们用字符串来接收值，方便找位数 result结果为我们要的那个数的
//        int value = result[(res - 1) % count]-'0';    //(n-1)%位数 得出我们要的第x位的数
//        return value;
    }

    public static void main(String[] args) {
        System.out.println(findNthDigit(11));
    }
}
