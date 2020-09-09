package com.todd.others;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author todd
 * @date 2020/7/9 6:24
 * @description: 生产者消费者模型用 使用Lock和Condition的await() / signal()方法
 */
public class ProducerAndConsumer2 {
    public static void main(String[] args) {
        Repository2 repository = new Repository2();
        Thread p1 = new Thread(new Producer2(repository), "p1");
        Thread p2 = new Thread(new Producer2(repository), "p2");
        Thread p3 = new Thread(new Producer2(repository), "p3");
        Thread c1 = new Thread(new Consumer2(repository), "c1");
        Thread c2 = new Thread(new Consumer2(repository), "c2");
        Thread c3 = new Thread(new Consumer2(repository), "c3");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

class Repository2 {
    private final int CAPACITY = 10;
    private static LinkedList<Object> queue = new LinkedList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition emptyCondition = lock.newCondition();
    private final Condition fullCondition = lock.newCondition();

    public void  produce() {

        lock.lock();
        try {
            while (queue.size() == CAPACITY) {
                System.out.println(Thread.currentThread().getName() + "[生产者]: 仓库已满:" + queue.size());
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            queue.offer(new Object());
            System.out.println(Thread.currentThread().getName() + "[生产者]: 生产产品:" + queue.size());
            fullCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                System.out.println(Thread.currentThread().getName() + "[消费者]: 仓库为空:" + queue.size());
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.poll();
            System.out.println(Thread.currentThread().getName() + "[消费者]: 消费产品:" + queue.size());
            fullCondition.signalAll();

        } finally {
            lock.unlock();
        }

    }
}

class Producer2 implements Runnable {
    private Repository2 repository;

    Producer2(Repository2 repository) {
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

class Consumer2 implements Runnable {
    private Repository2 repository;

    Consumer2(Repository2 repository) {
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