package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/2 9:15
 * @description: NC527
 * 牛牛刚刚学习了素数的定义，现在给定一个正整数N，牛牛希望知道N最少表示成多少个素数的和。
 * 素数是指在大于1的自然数中，除了1和它本身以外不再有其他因数的自然数。
 *
 * 简单题，判断素数就很好，如果为大于2偶数，那么直接返回2, 如果为大于2的基数且不是素数，那么由于素数中只有2一个偶数，因此减去2必定为偶数，
 * 再根据哥德巴赫猜想便可以得到个数。
 *
 */
public class NC527 {
    public int MinPrimeSum (int N) {
        if (judgePrime(N)) {
            return 1;
        } else if (N % 2 == 0) {
            return 2;
        } else {
            if (judgePrime(N - 2)) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public boolean judgePrime(int n) {
        if (n != 2 && (n & 1) == 0) {
            return false;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}

