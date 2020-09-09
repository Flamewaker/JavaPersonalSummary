package com.todd.concurrent.ReentrantLock;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 14:54
 * @description: 本例中由于m1锁定this, 只有m1执行完毕的时候, m2才能执行，这里是复习synchronized最原始的语义
 * ReentrantLock用于替代synchronized
 *
 */
public class ReentrantLock1 {
    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }

    }

    synchronized void m2() {
        System.out.println("m2 ...");
    }

    public static void main(String[] args) {
        ReentrantLock1 rl = new ReentrantLock1();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }
}
