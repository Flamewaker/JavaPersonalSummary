package com.todd.others;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author todd
 * @date 2020/7/9 7:15
 * @description: 信号量
 */
public class ProducerAndConsumer4 {

    public static void main(String[] args) {
        Repository4 repository = new Repository4();
        Thread p1 = new Thread(new Producer4(repository), "p1");
        Thread p2 = new Thread(new Producer4(repository), "p2");
        Thread p3 = new Thread(new Producer4(repository), "p3");
        Thread c1 = new Thread(new Consumer4(repository), "c1");
        Thread c2 = new Thread(new Consumer4(repository), "c2");
        Thread c3 = new Thread(new Consumer4(repository), "c3");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

class Repository4 {

    // 仓库存储的载体

    private LinkedList<Object> list = new LinkedList<Object>();

    // 仓库的最大容量

    final Semaphore notFull = new Semaphore(10);

    // 将线程挂起，等待其他来触发

    final Semaphore notEmpty = new Semaphore(0);

    // 互斥锁

    final Semaphore mutex = new Semaphore(1);


    public void produce() {
        try {
            notFull.acquire();
            mutex.acquire();
            list.add(new Object());
            System.out.println(Thread.currentThread().getName() + "[生产者]: 生产产品:" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notEmpty.release();
        }


    }

    public void consume() {
        try {
            notEmpty.acquire();
            mutex.acquire();
            list.poll();
            System.out.println(Thread.currentThread().getName() + "[消费者]: 消费产品:" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notFull.release();
        }
    }
}

class Producer4 implements Runnable {
    private Repository4 repository;

    Producer4(Repository4 repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                repository.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer4 implements Runnable {
    private Repository4 repository;

    Consumer4(Repository4 repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                repository.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


