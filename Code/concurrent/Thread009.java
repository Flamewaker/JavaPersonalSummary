package com.todd.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 11:14
 * @description: 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，
 * 再次申请的时候仍然会得到该对象的锁. 也就是说synchronized获得的锁是可重入的。
 * 同一个线程获得一个对象的锁还可以再获取该对象的锁。
 *
 * 当一个线程锁定对象A的锁，隔一段时间再去锁定线程B的锁。而另一个线程先去锁定B对象的锁，在去锁定A对象的锁，就产生了死锁。
 * 实际中，有循环锁定的时候可能会产生死锁。
 */
public class Thread009 {
    synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }
}
