package com.atguigu.design.behavioral.template;

/**
 * 1、定义模板
 */
public abstract class CookTemplate {

    /**
     * 定义算法：  定义好了模板
     * 父类可以实现某些步骤
     * 留关键给子类
     */
    public void cook(){
        //定义算法步骤
        heating();   //v
        addfood();
        addsalt();
        stirfry();   //v
        end();      //v
    }

    //加热方法
    public void heating(){
        System.out.println("开火...");
    };

    //添加食物
    public abstract void addfood();

    //加盐
    public abstract void addsalt();

    //翻炒
    public  void stirfry(){
        System.out.println("翻炒中...");
    };

    //出锅
    public  void end(){
        System.out.println("出锅....");
    };


}
