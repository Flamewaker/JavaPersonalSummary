package com.atguigu.design.behavioral.visitor;


/**
 * 升级包的接口
 */
public interface Vistor {


    //访问者能访问元素。
    void visitDisk(Disk disk);

    void visitCPU(CPU cpu);

    void visitFoot(Foot foot);
}
