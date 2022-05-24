/**
 * wulin口罩集团
 */
public abstract class WulinMaskFactory extends WulinFactory{
    @Override
    AbstractCar newCar() {
        return null;
    }


    abstract AbstractMask newMask();
}
