package com.todd.design.structural.adapter;

/**
 *
 * 适配器
 * 1、系统原有两个已存在接口 player、translate没有任何关系
 *
 * 需求，现在一个小....日本友人。看电影字幕是中文的不习惯。
 *
 * 2、我们在不改变原有系统的基础上实现这个功能就需要一个适配器
 *
 *  系统原来存在的所有接口都不能动。扩展一个新的类，来连接两个之前不同的类
 *
 */
public class MainTest {

    public static void main(String[] args) {

        //1、友人想要看电影带日文字幕
        MoviePlayer moviePlayer = new MoviePlayer();
        moviePlayer.play();
    }
}
