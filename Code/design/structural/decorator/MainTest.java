package com.todd.design.structural.decorator;

/**
 * 核心：想要不改变原来接口方法的情况下扩展新功能，或者增强方法.....
 */
public class MainTest {

    public static void main(String[] args) {
        //被装饰对象
        ManTikTok manTikTok = new LeiTikTok();
//        manTikTok.tiktok();

        /**
         *  LiMingTiktokProxy proxy = new LiMingTiktokProxy(new LeiTikTok());
         *         proxy.tiktok();
         */

        MeiYanDecorator decorator = new MeiYanDecorator(manTikTok);
        decorator.tiktok();
    }
}
