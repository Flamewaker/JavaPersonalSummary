package com.todd.design.creatation.factory.factorymethod;

import com.todd.design.creatation.factory.abstractfactory.RacingCar;
import com.todd.design.creatation.factory.simplefactory.AbstractCar;

/**
 * RacingCar分厂
 */
public class WulinRacingCarFactory extends AbstractCarFactory{
    @Override
    public AbstractCar newCar() {
        return new RacingCar();
    }
}
