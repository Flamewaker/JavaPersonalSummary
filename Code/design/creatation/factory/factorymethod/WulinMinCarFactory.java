package com.todd.design.creatation.factory.factorymethod;

import com.todd.design.creatation.factory.simplefactory.AbstractCar;
import com.todd.design.creatation.factory.simplefactory.MiniCar;

/**
 * minicar分厂
 */
public class WulinMinCarFactory extends AbstractCarFactory{
    @Override
    public AbstractCar newCar() {
        return new MiniCar();
    }
}
