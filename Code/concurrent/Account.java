package com.todd.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author todd
 * @date 2020/6/2 11:13
 * @description: 对业务写方法加锁，对业务读方法不加锁，容易产生脏读问题（dirtyRead）
 * 锁定过程中可以运行非同步方法，从而产生脏读问题。加锁影响性能。
 */
public class Account{
    String name;
    double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }


    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.set("zhangsan", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));
    }
}
