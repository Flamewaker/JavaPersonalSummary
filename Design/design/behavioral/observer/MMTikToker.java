package com.atguigu.design.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主播
 * 双向观察
 */
public class MMTikToker  extends AbstractTikToker{

    //1、观察者的核心1
    List<AbstractFans> fansList = new ArrayList<>();

    void startSell() {
        System.out.println("雷丰阳... 开始卖货...源码设计课");
        notifyFans("我开始卖东西了，是源码设计课，只要666");
    }
    void endSell() {
        System.out.println("雷丰阳... 结束卖货...源码设计课");
        notifyFans("课已经卖完了，记得五星好评...");
    }


    @Override
    void addFans(AbstractFans fans) {
        fansList.add(fans);
    }

    //通知所有观察者
    @Override
    void notifyFans(String msg) {
        //1、所有粉丝拿来通知
        for (AbstractFans fans : fansList) {
            fans.acceptMsg(msg);
        }
    }
}
