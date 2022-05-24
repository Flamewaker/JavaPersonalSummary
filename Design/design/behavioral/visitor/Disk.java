package com.atguigu.design.behavioral.visitor;

public class Disk extends Hardware{
    public Disk(String command) {
        super(command);
    }

    @Override
    public void work() {
        System.out.println("Disk保存指令的历史记录："+command);
    }


    @Override
    public void accept(Vistor vistor) {
        vistor.visitDisk(this);
    }

}
