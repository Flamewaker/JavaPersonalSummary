package com.atguigu.design.behavioral.memento;

import lombok.Data;

/**
 * 游戏记录。需要保存的内部状态信息，
 *
 *      也叫备忘录信息
 *
 *
 */
@Data
public class GameRecord {
    Integer id; //代表生成记录的id
    Integer coin;//剩余金币
    Integer hp;//血量
    Integer mp;//蓝量
    Integer level;//等级


    //获取当前备忘录信息
    void getCurrent(){

        System.out.println("coin："+coin+"；\t"+"hp："+hp+"；\t mp："+mp);
    }

}
