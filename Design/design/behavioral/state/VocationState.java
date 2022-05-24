package com.atguigu.design.behavioral.state;

/**
 * 休假状态
 */
public class VocationState implements TeamState {
    @Override
    public void playGame() {
        System.out.println("三亚旅游真舒服....饿了...不玩游戏");
        //状态流转

    }

    @Override
    public TeamState next() {
        return new BeafNodleState();
    }
}
