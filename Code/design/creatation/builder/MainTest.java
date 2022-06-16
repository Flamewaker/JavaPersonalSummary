package com.todd.design.creatation.builder;

public class MainTest {

    public static void main(String[] args) {

        AbstractBuilder builder = new XiaomiBuilder();


        //建造这个手机
//        builder.customMem("16G");
//        builder.customDisk("1T");
//        builder.customCam("2亿");
//        builder.customCpu("骁龙8个8");


        //链式建造者 Swagger

        Phone phone = builder.customCpu("骁龙8个8")
                .customCam("2亿")
                .customDisk("1T")
                .customMem("16G")
                .getProduct();
        System.out.println(phone);


        Phone build = Phone.builder()
                .cpu("1")
                .mem("2")
                .cam("3")
                .disk("4")
                .build();

        System.out.println(build);


    }
}
