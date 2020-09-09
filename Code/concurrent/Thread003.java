package com.todd.concurrent;

/**
 * @author todd
 * @date 2020/6/2 11:05
 * @description: synchronized关键字，对某个对象加锁，synchronized关键字锁定的是对象
 */
public class Thread003 {
    private int count = 10;

    //等同于在方法的代码执行时要synchronized(this)，等同于在执行代码时锁定当前对象。

    public synchronized void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
