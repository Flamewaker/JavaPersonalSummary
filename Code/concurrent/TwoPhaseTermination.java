package com.todd.concurrent;

/**
 * @author todd
 * @date 2020/6/5 14:22
 * @description: TODO
 */
public class TwoPhaseTermination {

    public static void main(String[] args) {
        TPTVolatile t = new TPTVolatile();
        t.start();
        t.start();
//        try {
//            Thread.sleep(3500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("停止监控");
//        t.stop();
    }

}

class TPTVolatile {
    private Thread thread;
    private volatile boolean stop = false;

    //判断是否执行过监控方法(犹豫模式balking)

    private volatile boolean starting = false;

    public void start() {
        //加入犹豫模式balking(这里类似单例模式，由于只需要一个监控线程进行监控)

        synchronized (this) {
            if (starting) {
                return;
            }
            starting = true;
        }

        //监控方法

        thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    System.out.println("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("进行监控操作");
                } catch (InterruptedException e) {

                }

                //System.out.println("进行监控操作");
            }
        }, "监控线程");
        thread.start();
    }

    public void stop() {
        stop = true;
        //如果设置为真，但是线程还在sleep的过程中，直接打断sleep过程，报出InterruptedException，此时直接进行下一次循环，获得stop值
        //从而尽快结束线程。
        thread.interrupt();
    }
}