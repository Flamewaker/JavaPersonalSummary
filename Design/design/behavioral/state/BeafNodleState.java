package com.atguigu.design.behavioral.state;

/**
 * 吃牛肉面状态
 */
public class BeafNodleState implements TeamState {

    @Override
    public void playGame() {
        System.out.println("饱饱的一顿牛肉面......中了诅咒，输了");
    }

    @Override
    public TeamState next() {
        return new MatchState();
    }
}
