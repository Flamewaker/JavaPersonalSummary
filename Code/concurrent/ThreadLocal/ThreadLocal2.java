package com.todd.concurrent.ThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 15:05
 * @description: ThreadLocal线程局部变量，自己线程自己用。维护一个状态的时候使用。
 */
public class ThreadLocal2 {
    //volatile static Person p = new Person();
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }

    static class Person {
        String name = "zhangsan";
    }
}
