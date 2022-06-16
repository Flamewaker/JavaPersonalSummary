package com.todd.design.creatation.factory.simplefactory;

/**
 * 具体产品
 */
public class VanCar extends AbstractCar{
    public VanCar(){
        this.engine = "单杠柴油机";
    }

    @Override
    public void run() {
        System.out.println(engine+"--》嗒嗒嗒....");
    }
}
