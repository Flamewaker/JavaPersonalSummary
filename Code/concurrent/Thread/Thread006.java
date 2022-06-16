package com.todd.concurrent.Thread;

/**
 * @author todd
 * @date 2020/6/2 11:13
 * @description: TODO
 */
public class Thread006 implements Runnable {
    private int count = 10;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            Thread006 t = new Thread006();
            new Thread(t, "THREAD" + i).start();
        }
    }
}
