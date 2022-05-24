/**
 * 抽象销售渠道
 * PhoneOnSale  ==howToSale
 * PhoneOffSale == howToSale
 * PhoneStudentSale = howToSale
 * PhonePDD == howToSale
 *
 *
 */
public abstract class AbstractSale {

    private String type;
    private Integer price;
    public AbstractSale(String type,Integer price){
        this.type = type;
        this.price = price;
    }

    String getSaleInfo(){
        return "渠道："+type+"==>"+"价格："+price;
    }

    void howToSale(){
        //都不一样
    }
}
