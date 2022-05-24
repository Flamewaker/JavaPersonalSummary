package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 461. 汉明距离
 * 1. 异或运算后统计1的个数
 * 2. 循环遍历，判断最低是否为1
 * 3. temp 循环与 temp-1 做按位与计算。n&(n-1)，其运算结果恰为把 n 的二进制位中的最低位的 1 变为 0 之后的结果。
 * @date 10:49 AM 2022/5/24
 */
public class LeetCode461 {
    public int hammingDistance(int x, int y) {
        int temp = x ^ y;
        int count = 0;
        while (temp != 0) {
            if ((temp & 1) == 1) {
                count++;
            }
            temp >>= 1;
        }
        return count;
    }

    public int hammingDistanceOptimized(int x, int y) {
        int temp = x ^ y;
        int count = 0;
        while (temp != 0) {
            count += temp & 1;
            temp >>= 1;
        }
        return count;
    }

    public int hammingDistanceOptimizedSec(int x, int y) {
        int temp = x ^ y;
        int count = 0;
        while (temp != 0) {
            count++;
            temp &= temp - 1;
        }
        return count;
    }
}
