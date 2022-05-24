package com.atguigu.design.behavioral.visitor;


/**
 * 小爱机器人
 */
public class XiaoAi {

    private CPU cpu = new CPU("武汉天气");
    private Disk disk = new Disk("武汉天气");
    private Foot foot = new Foot("武汉天气");

    void answerQuestion(){
        cpu.work();
        disk.work();
        foot.work();
    }



    //接受升级包
    public void acceptUpdate(Vistor aPackage) {

        //访问模式


        //升级CPU
        aPackage.visitCPU(cpu);
        aPackage.visitDisk(disk);
        aPackage.visitFoot(foot);

    }
}
