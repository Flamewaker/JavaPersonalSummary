package com.todd.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 11:14
 * @description: 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的，这里是继承中有可能发生的情形，子类调用父类的同步方法。这种锁定是允许的，相当于锁定同一个对象。
 */
public class Thread010 {
    synchronized void m() {
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new TT().m();
    }
}

class TT extends Thread010{
    @Override
    synchronized void m() {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}
