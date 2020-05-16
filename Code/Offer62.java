package com.todd;

import java.util.ArrayList;

/**
 * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer62 {
    /**
     * 模拟链表, 假设当前删除的位置是 idx，下一个删除的数字的位置是 idx + m。
     * 但是，由于把当前位置的数字删除了，后面的数字会前移一位，所以实际的下一个位置是 idx + m - 1。
     * 由于数到末尾会从头继续数，所以最后取模一下，就是 (idx + m - 1) (mod n)。
     * @param n
     * @param m
     * @return last
     */

    public int lastRemaining2(int n, int m) {
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int idx = 0;
        while (n > 1) {
            idx = (idx + m - 1) % n;
            list.remove(idx);
            n--;
        }
        return list.get(0);
    }

    /**
     * 数学解法: (当前索引值 + m) % 上一轮剩余数字的个数
     * 最后一轮剩下2个人，所以从2开始反推。
     * 当只剩下一位的时候，就是所需要的结果，此结果的当前索引值为0。
     * 根据当前索引值可以进行反推，得到该值在上一轮中的索引值。也就是：上一轮的索引值 = (当前索引值 + m) % 上一轮剩余数字的个数
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        if(n < 1 || m < 1) {
            return -1;
        }
        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }
        return last;
    }

    public int f(int n, int m){
        if(n == 1){
            return 0;
        }
        int x = f(n - 1, m);
        return (x + m) % n;
    }
    public int lastRemaining3(int n, int m) {
        return f(n, m);
    }
}
