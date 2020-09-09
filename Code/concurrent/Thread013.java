package com.todd.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author todd
 * @date 2020/6/2 12:16
 * @description: volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * volatile只保证可见性，不保证原子性。而synchronized均可以保证。synchronized性能低不少。
 *
 * t.join()方法只会使主线程进入等待池并等待t线程执行完毕后才会被唤醒。并不影响同一时刻处在运行状态的其他线程。
 * https://blog.csdn.net/u013425438/article/details/80205693
 */
public class Thread013 {
    volatile int count = 0;

    //如下的自增操作不是原子操作

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        Thread013 t = new Thread013();

        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);


    }
}
