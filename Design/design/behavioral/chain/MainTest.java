package com.atguigu.design.behavioral.chain;

/**
 *         //1、链条的引用点
 *         //2、下一个继续
 *         //3、构造链条
 *
 *   回旋责任链
 *   Filter：1 -- 2 -- 3 -- 本人 -- 3 -- 2 -- 1
 *
 */
public class MainTest {

    public static void main(String[] args) {

        Teacher leifengyang = new Teacher("Leifengyang");

        Teacher xiaokui = new Teacher("xiaokui");

        Teacher mengmeng = new Teacher("mengmeng");


        leifengyang.setNext(xiaokui);
        xiaokui.setNext(mengmeng);



        leifengyang.handleRequest();


    }
}
