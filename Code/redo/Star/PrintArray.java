package com.todd.redo.Star;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author todd
 * @date 2020/8/25 23:16
 * @description: 两个线程交替打印两个数组中的元素
 */
public class PrintArray {
    public static void main(String[] args) {
        char[] digit = new char[]{'1','2','3','4','5','6','7','8','9'};
        char[] letter = new char[]{'A','B','C','D','E','F','G','H','I'};

        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < digit.length; i++) {
                    System.out.println(digit[i]);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < letter.length; i++) {
                    System.out.println(letter[i]);
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
