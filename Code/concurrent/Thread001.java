package com.todd.concurrent;

/**
 * @author todd
 * @date 2020/6/2 10:56
 * @description: synchronized关键字，对某个对象加锁，synchronized关键字锁定的是对象
 */
public class Thread001 {
    private int count = 10;

    //堆内存中的对象

    private Object o = new Object();
    public void m() {

        //任何线程要执行下面的代码，必须先拿到o的锁

        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count =" + count);
        }
    }
}
