package com.todd.design.creatation.factory.abstractfactory;

/**
 * 工厂的产品
 *
 * 怎么把一个功能提升一个层次：定义抽象（抽象类，接口）
 * 抽象类，接口  就会有多实现，多实现自然就有多功能
 */
public abstract class AbstractCar {

    String engine;
    public abstract void run();
}
