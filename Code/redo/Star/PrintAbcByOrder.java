package com.todd.redo.Star;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author todd
 * @date 2020/8/25 22:23
 * @description: 多线程打印ABC
 */
public class PrintAbcByOrder {
    public static void main(String[] args) {
        Print p = new Print();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                p.printC();
            }
        }, "C").start();
    }
}

class Print {
    private ReentrantLock lock = new ReentrantLock();
    Condition a = lock.newCondition();
    Condition b = lock.newCondition();
    Condition c = lock.newCondition();
    private int statue = 1;

    public void printA() {
        lock.lock();
        try {
            while (statue != 1) {
                a.await();
            }
            System.out.println("A");
            statue = 2;
            b.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (statue != 2) {
                b.await();
            }
            System.out.println("B");
            statue = 3;
            c.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (statue != 3) {
                c.await();
            }
            System.out.println("C");
            statue = 1;
            a.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}