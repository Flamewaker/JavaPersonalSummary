/**
 * wulin 汽车集团
 */
public  abstract  class WulinCarFactory extends WulinFactory{
    @Override
    abstract  AbstractCar newCar();

    @Override
    AbstractMask newMask() {
        return null;
    }
}
