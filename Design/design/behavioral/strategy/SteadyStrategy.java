package com.atguigu.design.behavioral.strategy;


/**
 * 稳健运营策略
 */
public class SteadyStrategy implements GameStrategy {
    @Override
    public void warStrategy() {
        System.out.println("各路小心...及时支援...");
    }
}
