/**
 * 抽象出来。
 *      可以抽象成接口（只有方法），可以抽象成抽象类（有些属性也需要用）
 */
public class MainTest {

    public static void main(String[] args) {

        //
        WulinFactory wulinFactory = new WulinWuHanMaskFactory();
        AbstractCar abstractCar = wulinFactory.newCar();

        AbstractMask abstractMask = wulinFactory.newMask();
        abstractMask.protectedMe();


        wulinFactory = new WulinHangZhouMaskFactory();
        AbstractMask abstractMask1 = wulinFactory.newMask();
        abstractMask1.protectedMe();
    }
}
