package com.todd.redo.Star;

import sun.awt.windows.ThemeReader;

/**
 * @author todd
 * @date 2020/8/25 22:53
 * @description: 创建3个线程，t1、t2、t3，让让t1在t2之前执行，t2在t3之前执行
 */
public class Join {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }, "1");

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName());
        }, "2");

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName());
        }, "3");

        t1.start();
        t2.start();
        t3.start();
    }
}
