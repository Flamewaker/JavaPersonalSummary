/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 *
 * @author mashibing
 */
package com.todd.concurrent.MyContainer2;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用wait和notify/notifyAll来实现，wait和notify调用时必须上锁。
 *
 * wait和while一般一起使用
 *
 * @author todd
 */

public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    //最多10个元素

    final private int MAX = 10;
    private int count = 0;

	//方法不能上锁，锁的是对象。可以有很对线程进行等待。

    public synchronized void put(T t) {
        //想想为什么用while而不是用if？一个被唤醒开始进行add操作，另一个线程也add一个就出问题了。多个线程在wait，唤醒后，继续执行下面的代码，而没有进行判断。
		//notifyAll虚假唤醒
        while (lists.size() == MAX) {
            try {
                this.wait(); //effective java
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;
        this.notifyAll(); //通知消费者线程进行消费，如果是notify，如果唤醒的是生产者，可能会产生死锁。永远要使用notifyAll
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll(); //通知生产者进行生产
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> c = new MyContainer1<>();
        //启动消费者线程

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                	c.put(Thread.currentThread().getName() + " " + j);
				}
            }, "p" + i).start();
        }
    }
}
