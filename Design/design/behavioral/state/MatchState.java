package com.atguigu.design.behavioral.state;

/**
 * 竞赛状态
 */
public class MatchState implements TeamState{
    @Override
    public void playGame() {
        System.out.println("全力以赴打比赛....");
    }


    //状态模式的核心
    @Override
    public TeamState next() {
        return new VocationState();
    }
}
