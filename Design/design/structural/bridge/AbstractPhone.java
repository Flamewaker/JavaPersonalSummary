/**
 * 1、抽象手机类
 *  手机有各种销售渠道价格都不一样
 */
public abstract class AbstractPhone {

    //桥接在此.....设计期间就得想好
    //【真正会引起此类变化的一个维度直接抽取出来，通过组合的方式接起来】
    //桥接+适配器 ...
    AbstractSale sale; //分离渠道【桥接的关注点】
//    abstract int getPrice();  如果这么写需要多少个实现。违反开闭原则


    /**
     * 当前手机的描述
     * @return
     */
    abstract String getPhone();

    public void setSale(AbstractSale sale) {
        this.sale = sale;
    }
}
