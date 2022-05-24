package com.atguigu.design.behavioral.command;


/**
 * 抽象命令类
 * Controller、Service、Dao接口
 *
 * Service
 */
public interface  Command {

    /**
     * 命令的执行方法
     */
    void execute();

}
