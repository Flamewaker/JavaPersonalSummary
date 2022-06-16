package com.todd.design.structural.proxy.dynamic;

public class LeiTikTok  implements ManTikTok, SellTikTok {
//    @Override
    public void tiktok() {
        System.out.println("雷丰阳，tiktok.... ");
    }

    @Override
    public void sell() {
        System.out.println("雷丰阳，只要666，赶紧来包...");
    }

    public void haha(){
        System.out.println("hahaha ,......");
    }
}
