package com.atguigu.design.behavioral.interpreter;

import java.util.Set;

/**
 * 终结符表达式
 *
 * 多少种解析规则就需要定义多少种规则类
 *
 */
public class TerminalExpression extends IDCardExpression {

    IDCardExpression childExp;

    Set<String> data;  //免费数据
    String symbol; //定义解析用的符号如  ： -

    public TerminalExpression( Set<String>  data,String symbol){
        this.data = data;
        this.symbol = symbol;
    }

    @Override
    boolean interpret(String expression) {
        //上海市：张文宏-医生
        //1、先按照指定符号分割
        String[] split = expression.split(symbol);

        // 冒号：  上海市              张文宏-医生
        // 短横线  上海市：张文宏       医生
        boolean result = false;
        for (String s : split) {
            if(data.contains(s)){
                return true;
            };//说明是免费的信息里面的
        }

        //可以继续子解析
//        childExp.interpret(expression);

        //不在免费行列
        return false;
    }
}
