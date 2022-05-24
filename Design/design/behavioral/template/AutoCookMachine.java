package com.atguigu.design.behavioral.template;

public class AutoCookMachine extends CookTemplate{

    @Override
    public void addfood() {
        System.out.println("放了三个小白菜");
    }

    @Override
    public void addsalt() {
        System.out.println("放了三勺盐");
    }
}
