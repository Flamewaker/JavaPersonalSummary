package com.atguigu.design.behavioral.observer;

/**
 * 抖音主播
 *
 * 粉丝观察主播....
 */
public abstract class AbstractTikToker {

    //添加粉丝
    abstract void addFans(AbstractFans fans);

    //通知粉丝
    abstract void notifyFans(String msg);
}
