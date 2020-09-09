package com.todd.concurrent.ThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 15:05
 * @description: ThreadLocal线程局部变量
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
 */
public class ThreadLocal1 {
    volatile static Person p = new Person();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(p.name);
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "lisi";
        }).start();
    }
}

class Person {
    String name = "zhangsan";
}
