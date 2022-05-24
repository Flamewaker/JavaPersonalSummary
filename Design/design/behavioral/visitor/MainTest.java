package com.atguigu.design.behavioral.visitor;

public class MainTest {

    public static void main(String[] args) {

        XiaoAi xiaoAi = new XiaoAi();

        xiaoAi.answerQuestion();


        //升级。cpu联网处理指令
        //升级。disk保存到云存储
        UpdatePackage aPackage = new UpdatePackage("联网增强功能");
        xiaoAi.acceptUpdate(aPackage);



        //访问者
        xiaoAi.answerQuestion();




    }
}
