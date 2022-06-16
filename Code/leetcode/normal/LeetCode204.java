package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author todd
 * @date 2020/8/9 16:18
 * @description: 统计所有小于非负整数 n 的质数的数量。
 * 素数的定义很简单，如果一个数如果只能被 1 和它本身整除，那么这个数就是素数。
 */
public class LeetCode204 {
    public int countPrimes(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                // 有其他整除因子
                return false;
            }
        }
        return true;
    }

    public int countPrimes2(int n) {
        boolean[] isPrim  = new boolean[n + 1];
        int count = 0;
        Arrays.fill(isPrim, true);
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                count++;
                for (int j = i * 2; j < n; j += i) {
                    isPrim[j] = false;
                }
            }
        }
        return count;
    }

    public int countPrimes3(int n) {
        boolean[] isPrim  = new boolean[n + 1];
        Arrays.fill(isPrim, true);
        for (int i = 2; i * i < n; i++) {
            if (isPrim[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrim[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrim[i]) {
                count++;
            }
        }

        return count;
    }
}
