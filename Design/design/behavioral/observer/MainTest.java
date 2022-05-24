package com.atguigu.design.behavioral.observer;

public class MainTest {

    public static void main(String[] args) {

        MMTikToker lei = new MMTikToker();

        lei.startSell();

        RobotFans fans1 = new RobotFans();
        RobotFans fans2 = new RobotFans();
        RobotFans fans3 = new RobotFans();
        fans1.follow(lei);
        fans2.follow(lei);
        fans3.follow(lei);

        HumanFans humanFans = new HumanFans();
        humanFans.follow(lei);

        System.out.println("=====>");


        lei.endSell();
    }
}
