package com.atguigu.design.behavioral.strategy;


/**
 * 环境类
 */
public class TeamGNR {

    //抽取游戏策略算法，并进行引用
    private GameStrategy gameStrategy;

    public void setGameStrategy(GameStrategy gameStrategy) {
        this.gameStrategy = gameStrategy;
    }

    public void startGame(){
        System.out.println("游戏开始.....");
        //游戏策略
        gameStrategy.warStrategy();
        System.out.println("win......");
    }
}
