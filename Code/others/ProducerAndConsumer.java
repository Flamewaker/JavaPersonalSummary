package com.todd.others;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/7/8 22:01
 * @description: 生产者消费者模型用 使用使用Object的wait()/notifyAll()方法
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        Repository1 repository = new Repository1();
        Thread p1 = new Thread(new Producer1(repository), "p1");
        Thread p2 = new Thread(new Producer1(repository), "p2");
        Thread p3 = new Thread(new Producer1(repository), "p3");
        Thread c1 = new Thread(new Consumer1(repository), "c1");
        Thread c2 = new Thread(new Consumer1(repository), "c2");
        Thread c3 = new Thread(new Consumer1(repository), "c3");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }


}

class Repository1 {
    private final int CAPACITY = 10;
    private static LinkedList<Object> queue = new LinkedList<>();

    public void  produce() {
        synchronized (queue) {
            while (queue.size() == CAPACITY) {
                System.out.println(Thread.currentThread().getName() + "[生产者]: 仓库已满:" + queue.size());
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(new Object());
            System.out.println(Thread.currentThread().getName() + "[生产者]: 生产产品:" + queue.size());
            queue.notifyAll();
        }
    }

    public void consume() {
        synchronized (queue) {
            while (queue.size() == 0) {
                System.out.println(Thread.currentThread().getName() + "[消费者]: 仓库为空:" + queue.size());
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.poll();
            System.out.println(Thread.currentThread().getName() + "[消费者]: 消费产品:" + queue.size());
            queue.notifyAll();
        }
    }
}

class Producer1 implements Runnable {
    private Repository1 repository;

    Producer1(Repository1 repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                repository.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer1 implements Runnable {
    private Repository1 repository;

    Consumer1(Repository1 repository) {
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