package com.todd.concurrent;

/**
 * @author todd
 * @date 2020/6/2 11:12
 * @description: synchronized的代码块是原子操作。
 */
public class Thread005 implements Runnable {
    private int count = 10;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        Thread005 t = new Thread005();

        //5个线程访问同一个对象

        for (int i = 0; i < 5; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
}
