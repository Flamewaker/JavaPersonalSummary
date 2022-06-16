package com.todd.design.structural.flyweight;

/**
 * 可共享和不可共享状态
 */
public abstract class AbstractWaitressFlyweight {

    boolean canService = true;//能否服务

    //正在服务。   享元的不可共享属性留给外部进行改变的接口
    abstract void service();
    //服务完成。   享元的不可共享属性留给外部进行改变的接口
    abstract void end();

    public boolean isCanService() {
        return canService;
    }
}
