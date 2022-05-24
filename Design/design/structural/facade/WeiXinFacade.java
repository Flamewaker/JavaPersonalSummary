package com.atguigu.design.structural.facade;

public class WeiXinFacade {

    Police police = new Police();
    Edu edu = new Edu();
    Social social = new Social();


    /**
     * 封装起来只留一个方法
     * @param name
     */
    public void handleXxx(String name){
        police.resgister(name);
        edu.assignSchool(name);
        social.handleSocial(name);
    }

    public void resgister(String name){
        police.resgister(name);
    }
    public void assignSchool(String name){
        edu.assignSchool(name);
    }

}
