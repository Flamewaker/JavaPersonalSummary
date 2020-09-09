package com.todd.others;


import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author todd
 * @date 2020/7/9 6:24
 * @description: 使用BlockingQueue阻塞队列方法
 */
public class ProducerAndConsumer3 {
    public static void main(String[] args) {
        Repository3 repository = new Repository3();
        Thread p1 = new Thread(new Producer3(repository), "p1");
        Thread p2 = new Thread(new Producer3(repository), "p2");
        Thread p3 = new Thread(new Producer3(repository), "p3");
        Thread c1 = new Thread(new Consumer3(repository), "c1");
        Thread c2 = new Thread(new Consumer3(repository), "c2");
        Thread c3 = new Thread(new Consumer3(repository), "c3");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }

}

class Repository3 {
    private final int CAPACITY = 10;
    private LinkedBlockingDeque<Object> blockingQueue = new LinkedBlockingDeque<>(CAPACITY);

    public void produce() {
        try {
            blockingQueue.put(new Object());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "[生参者]: 生产产品:" + blockingQueue.size());

    }

    public void consume() {
        try {
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "[消费者]: 消费产品:" + blockingQueue.size());
    }
}

class Producer3 implements Runnable {
    private Repository3 repository;

    Producer3(Repository3 repository) {
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

class Consumer3 implements Runnable {
    private Repository3 repository;

    Consumer3(Repository3 repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                repository.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}