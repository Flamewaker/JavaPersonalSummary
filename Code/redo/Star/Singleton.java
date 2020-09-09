package com.todd.redo.Star;

/**
 * @author todd
 * @date 2020/8/24 13:48
 * @description: 单例模式的多种不同的实现
 */

class Singleton1 {
    private static Singleton1 instance = new Singleton1();
    private Singleton1() {}
    public static Singleton1 getInstance() {
        return instance;
    }
}

class Singleton2 {
    private static Singleton2 instance;
    private Singleton2() {}
    public static synchronized Singleton2 getSingleton() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

class Singleton3 {
    private static volatile Singleton3 instance;
    private Singleton3() {}
    public static Singleton3 getSingleton() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}

class Singleton4 {
    private Singleton4() {}
    private static class Inner {
        static final Singleton4 instance = new Singleton4();
    }
    public static Singleton4 getSingleton() {
        return Inner.instance;
    }

}

