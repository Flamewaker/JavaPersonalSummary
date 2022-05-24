package com.atguigu.design.behavioral.interpreter;


import java.util.HashSet;
import java.util.Set;

/**
 * 环境类：
 * 风景区
 */
public class Area {
    Set<String> city = new HashSet<>();


    Set<String> type = new HashSet<>();
    IDCardExpression idCardReader;//读卡器，表达式解析器

    //环境类持有表达式

    public Area(){
//        {"武汉市","上海市"};//免费城市
//        {"医生","老人","儿童"};//免费人群
        city.add("武汉市");
        city.add("上海市");

        type.add("医生");
//        type.add("老人");
        type.add("儿童");
        //最小解析
        TerminalExpression city =
                new TerminalExpression(this.city, ":");
        //最小解析
        TerminalExpression type =
                new TerminalExpression(this.type, "-");


        //以上满足一个即可
        idCardReader = new OrExpression(city,type);

    }

    /**
     * 传入表达式，判断当前表达式是否指定为免费人群
     * @param expression
     */
    void getTicket(String expression){
        boolean interpret = idCardReader.interpret(expression);
        if(interpret){
            System.out.println("恭喜你，免票通过....");
        }else {
            System.out.println("对不起，请2元购票....");
        }
    }
}
