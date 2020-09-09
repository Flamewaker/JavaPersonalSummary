package com.todd.concurrent.TicketSeller;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 15:10
 * @description: 使用Vector或者Collections.synchronizedXXX
 * 有问题，判断和操作分离，remove操作是原子性的，判断size>0, 之间会产生问题。
 */
public class TicketSeller2 {
    static Vector<String> tickets = new Vector<>();


    static {
        for(int i=0; i<1000; i++) {
            tickets.add("票 编号：" + i);
        }
    }

    public static void main(String[] args) {

        for(int i=0; i<10; i++) {
            new Thread(()->{
                while(tickets.size() > 0) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
