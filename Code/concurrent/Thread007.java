package com.todd.concurrent;

/**
 * @author todd
 * @date 2020/6/2 11:13
 * @description: 同步和非同步方法是否可以同时调用？同步方法和非同步方法可以同时调用
 *
 */
public class Thread007 {
    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 ");
    }

    public static void main(String[] args) {
        Thread007 t = new Thread007();

        //下面两个是java8写法

        //下面的可以运行，synchronized的m1才需要锁，m2的不需要锁可以执行

		new Thread(()->t.m1(), "t1").start();
		new Thread(()->t.m2(), "t2").start();

//        new Thread(t::m1, "t1").start();
//        new Thread(t::m2, "t2").start();

        //下面是最原始代码

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				t.m1();
//			}
//
//		});


    }
}
