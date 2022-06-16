package com.todd.concurrent.Thread;

/**
 * @author todd
 * @date 2020/6/2 11:02
 * @description: synchronized关键字，对某个对象加锁，synchronized关键字锁定的是对象
 */
public class Thread002 {
    private int count = 10;

    public void m() {

        //任何线程要执行下面的代码，必须先拿到this的锁

        synchronized(this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
