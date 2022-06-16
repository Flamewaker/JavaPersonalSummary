package com.todd.design.creatation.factory.factorymethod;

public class MainTest {

    public static void main(String[] args) {
        AbstractCarFactory carFactory = new WulinRacingCarFactory();
        AbstractCar abstractCar = carFactory.newCar();
        abstractCar.run();

        carFactory = new WulinVanCarFactory();
        AbstractCar abstractCar1 = carFactory.newCar();
        abstractCar1.run();
    }
}
