package com.atguigu.design.behavioral.visitor;

public class CPU  extends Hardware{
    public CPU(String command) {
        super(command);
    }

    @Override
    public void work() {
        System.out.println("CPU处理指令："+command);
    }

    @Override
    public void accept(Vistor vistor) {
        //给升级包提供一个改CPU指令等信息的办法
        vistor.visitCPU(this);
    }

}
