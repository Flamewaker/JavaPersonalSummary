package com.atguigu.design.behavioral.visitor;


/**
 * 硬件。      抽象元素类
 */
public  abstract class Hardware {
    String command;//封装硬件的处理指令

    public Hardware(String command){
        this.command = command;
    }


    //收到命令以后进行工作
    abstract public void work();

    //定义接受软件升级包的方法。这个方法应该具体硬件去实现
     abstract public void accept(Vistor vistor);
}
