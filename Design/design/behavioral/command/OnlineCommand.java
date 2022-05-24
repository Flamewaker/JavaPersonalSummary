package com.atguigu.design.behavioral.command;

/**
 * 线上课命令
 *
 * Controller {
 *     xxService
 *     aaService
 *
 *
 *     //宏命令
 *     order(){
 *         //结账
 *         //扣库存
 *         //出账单
 *         ....
 *     }
 *
 * }
 */
public class OnlineCommand implements Command{

    //Dao
    private LeiReceiver receiver = new LeiReceiver();
    @Override
    public void execute() {
        System.out.println("要去上（吹）课（牛）....");
        receiver.online();
    }
}
