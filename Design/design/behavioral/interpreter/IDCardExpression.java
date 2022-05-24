package com.atguigu.design.behavioral.interpreter;

/**
 * 身份信息表达式
 * 表达式的解析
 *
 */
public abstract class IDCardExpression {

    /**
     * 定义解析逻辑
     * 假设我们需要解析的信息格式为：
     *      上海市：张文宏-医生
     *      武汉市：雷丰阳-程序员
     *      北京市：宋宋-老人
     *  表达式中，“：”以前的是城市，“-”以后的是职业
     * @param expression
     * @return
     */
    abstract boolean interpret(String expression);

}
