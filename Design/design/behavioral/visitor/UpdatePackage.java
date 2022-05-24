package com.atguigu.design.behavioral.visitor;

/**
 * 升级包可以更改指令
 */
public class UpdatePackage implements Vistor{


    private String ext;
    public  UpdatePackage(String ext){
        this.ext = ext;
    }

    @Override
    public void visitDisk(Disk disk) {
        disk.command += " >>> "+ext;
    }

    @Override
    public void visitCPU(CPU cpu) {
        //改属性为例
        cpu.command += ">>>> "+ext;
        //装饰模式。改方法
    }

    @Override
    public void visitFoot(Foot foot) {
        foot.command += " >>>> "+ext;
    }
}
