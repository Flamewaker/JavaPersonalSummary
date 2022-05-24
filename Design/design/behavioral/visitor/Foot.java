package com.atguigu.design.behavioral.visitor;

public class Foot extends Hardware{
    public Foot(String command) {
        super(command);
    }

    @Override
    public void work() {
        System.out.println("脚处理指令："+command);
    }


    //元素需要接受一个访问者的访问
    @Override
    public void accept(Vistor vistor) {
        vistor.visitFoot(this);
    }
}
