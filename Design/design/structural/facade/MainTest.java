package com.atguigu.design.structural.facade;


/**
 * 需求：来回跑太麻烦，按照最少知道原则，我就想和一个部门进行交互。
 *
 *
 *
 */
public class MainTest {

    public static void main(String[] args) {

//        Police police = new Police();
//        police.resgister("雷丰阳");
//
//        Edu edu = new Edu();
//        edu.assignSchool("雷丰阳");
//
//        Social social = new Social();
//        social.handleSocial("雷丰阳");

        WeiXinFacade facade = new WeiXinFacade();

        facade.handleXxx("雷丰阳");
//
//        facade.resgister("");
//        facade.assignSchool("");

    }
}
