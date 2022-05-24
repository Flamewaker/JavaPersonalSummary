package com.atguigu.design.behavioral.mediator;

import java.util.List;

/**
 * 海南8778
 */
public class HU8778 extends Captain{

    ControlTower controlTower;

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }

    @Override
    void fly() {
        System.out.println("HU8778请求起飞......");
        //问每个机长能否起飞？
        controlTower.acceptRequest(this,"fly");
    }

    @Override
    void land() {
        System.out.println("HU8778请求降落......");
        controlTower.acceptRequest(this,"land");
    }

    @Override
    void success() {
        System.out.println("完成......");
        controlTower.acceptRequest(this,"success");
    }
}
