package com.todd.design.creatation.factory.factorymethod;

import com.todd.design.creatation.factory.simplefactory.AbstractCar;

/**
 * 抽象工厂的层级
 */
public abstract class AbstractCarFactory {

    public abstract AbstractCar newCar();
    //我能造口罩.....
}
