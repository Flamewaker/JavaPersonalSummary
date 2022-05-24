package com.atguigu.design.behavioral.mediator;

/**
 * 四川8633机长
 */
public class SC8633 extends Captain{
    ControlTower controlTower ;

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }

    @Override
    void fly() {
        System.out.println("SC8633 请求起飞......");
        //问每个机长能否起飞？
        controlTower.acceptRequest(this,"fly");

    }

    @Override
    void land() {

        System.out.println("SC8633 请求降落......");
        //问每个机长能否起飞？
        controlTower.acceptRequest(this,";land");
    }

    @Override
    void success() {

        System.out.println("SC8633 完成......");
        //问每个机长能否起飞？
        controlTower.acceptRequest(this,"fly");
    }
}
