package com.todd.others;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author todd
 * @date 2020/6/2 15:08
 * @description: 线程安全的单例模式，单例模式：单例模式在内存中永远只有一个对象。单例模式就是说系统中对于某类的只能有一个对象，不可能出来第二个。
 * http://www.cnblogs.com/xudong-bupt/p/3433643.html
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 * 饿汉就是类一旦加载，就把单例初始化完成，保证getInstance的时候，单例是已经存在的了。
 * 懒汉比较懒，只有当调用getInstance的时候，才回去初始化这个单例。
 */
public class Singleton {
    private Singleton() { }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    public static Singleton getSingle() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                System.out.println(Singleton.getSingle());
            });
        }

        Arrays.asList(ths).forEach(o -> o.start());
    }
}

// 问题1：为什么加 final: 防止继承的子类改变单例
// 问题2：如果实现了序列化接口, 还要做什么来防止反序列化破坏单例
// 问题3：为什么设置为私有? 是否能防止反射创建新的实例? : 不能
// 问题4：这样初始化是否能保证单例对象创建时的线程安全? : 静态对象的初始化是在类加载时完成的。
// 问题5：为什么提供静态方法而不是直接将 INSTANCE 设置为 public, 说出你知道的理由 : 提供更好的封装性

final class Singleton1 implements Serializable {
    private Singleton1() {}
    private static Singleton1 INSTANCE = new Singleton1();
    public static Singleton1 getInstance() {
        return INSTANCE;
    }
    public Object readResolve() {
        return INSTANCE;
    }
}

// 分析这里的线程安全, 并说明有什么缺点

final class Singleton2 {
    private Singleton2() { }
    private static Singleton2 INSTANCE = null;

    public static synchronized Singleton2 getInstance() {
        if( INSTANCE == null ){
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }
}
//双重校验锁
// 问题1：解释为什么要加 volatile ?
// 问题2：对比实现2, 说出这样做的意义。
// 问题3：为什么还要在这里加为空判断, 之前不是判断过了吗.
// 执行双重检查是因为，如果多个线程同时了通过了第一次检查，并且其中一个线程首先通过了第二次检查并实例化了对象，那么剩余通过了第一次检查的线程就不会再去实例化对象。
// 这样，除了初始化的时候会出现加锁的情况，后续的所有调用都会避免加锁而直接返回，解决了性能消耗的问题。

final class Singleton3 {
    private Singleton3() { }
    private static volatile Singleton3 INSTANCE = null;
    public static Singleton3 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton3.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton3();
                }
            }
        }
        return INSTANCE;
    }
}

// 问题1：属于懒汉式还是饿汉式
// 问题2：在创建时是否有并发问题

final class Singleton4 {
    private Singleton4() { }
    private static class LazyHolder {
        static final Singleton4 INSTANCE = new Singleton4();
    }
    public static Singleton4 getInstance() {
        return LazyHolder.INSTANCE;
    }
}


