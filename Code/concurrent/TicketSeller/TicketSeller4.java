package com.todd.concurrent.TicketSeller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author todd
 * @date 2020/6/2 15:10
 * @description: 使用ConcurrentQueue提高并发性
 *
 * 1：Vector Hashtable ：早期使用synchronized实现
 * 2：ArrayList HashSet ：未考虑多线程安全（未实现同步）
 * 3：HashSet vs Hashtable StringBuilder vs StringBuffer
 * 4：Collections.synchronized***工厂方法使用的也是synchronized
 *
 * 使用早期的同步容器以及Collections.synchronized***方法的不足之处，请阅读：
 * http://blog.csdn.net/itm_hadf/article/details/7506529
 *
 * 使用新的并发容器
 * http://xuganggogo.iteye.com/blog/321630
 */
public class TicketSeller4 {
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票 编号：" + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //判断之后没有对队列进行修改操作
                while (true) {
                    String s = tickets.poll();
                    if (s == null) {
                        break;
                    } else {
                        System.out.println("销售了--" + s);
                    }
                }
            }).start();
        }
    }
}
