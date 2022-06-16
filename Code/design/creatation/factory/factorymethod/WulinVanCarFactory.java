package com.todd.design.creatation.factory.factorymethod;

import com.todd.design.creatation.factory.simplefactory.AbstractCar;
import com.todd.design.creatation.factory.simplefactory.VanCar;

public class WulinVanCarFactory extends AbstractCarFactory {
    @Override
    public AbstractCar newCar() {
        return new VanCar();
    }
}
