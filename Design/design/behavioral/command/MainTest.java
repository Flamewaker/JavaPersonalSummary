package com.atguigu.design.behavioral.command;

public class MainTest {

    public static void main(String[] args) {


//        LeiReceiver leiReceiver = new LeiReceiver();
//        leiReceiver.travel();

        TeacherTongInvoker invoker = new TeacherTongInvoker();
        invoker.setCommand(new OnlineCommand());

        invoker.call();
    }
}
