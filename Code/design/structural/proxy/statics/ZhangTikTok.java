package com.todd.design.structural.proxy.statics;

import com.todd.design.structural.decorator.ManTikTok;

public class ZhangTikTok implements ManTikTok {
    @Override
    public void tiktok() {
        System.out.println("张三，tiktok.... ");
    }
}
