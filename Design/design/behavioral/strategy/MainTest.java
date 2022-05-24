package com.atguigu.design.behavioral.strategy;

public class MainTest {

    public static void main(String[] args) {

        TeamGNR gnr = new TeamGNR();

        gnr.setGameStrategy(new RandomStrategy());
        gnr.startGame();
    }
}
