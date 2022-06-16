package com.todd.others;

import sun.awt.windows.ThemeReader;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/24 13:47
 * @description: 消费者生产者模式
 */
public class ProducerAndComsumer {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Thread t1 = new Thread(new Producer(repository), "t1");
        Thread t2 = new Thread(new Producer(repository), "t2");
        Thread t3 = new Thread(new Producer(repository), "t3");
        Thread t4 = new Thread(new Consumer(repository), "t4");
        Thread t5 = new Thread(new Consumer(repository), "t5");
        Thread t6 = new Thread(new Consumer(repository), "t6");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}

class Repository {
    int capacity = 10;
    LinkedList<Object> queue = new LinkedList<>();

    public void produce() {
        synchronized (queue) {
            if (queue.size() == capacity) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(new Object());
            queue.notifyAll();
        }
    }

    public void consume() {
        synchronized (queue) {
            if (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.poll();
            queue.notifyAll();
        }
    }
}

class Producer implements Runnable {

    Repository repository;

    public Producer(Repository repository) {
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

class Consumer implements Runnable {

    Repository repository;

    public Consumer(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                repository.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


