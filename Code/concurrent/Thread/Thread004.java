package com.todd.concurrent.Thread;

/**
 * @author todd
 * @date 2020/6/2 11:06
 * @description: synchronized关键字，对某个对象加锁，synchronized关键字锁定的是对象
 */
public class Thread004 {
    private static int count = 10;

    //这里等同于synchronized(concurrent004.class)

    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {

        //考虑一下这里写synchronized(this)是否可以？不行，静态方法和属性不需要new出来访问。

        synchronized(Thread004.class) {
            count --;
        }
    }
}
