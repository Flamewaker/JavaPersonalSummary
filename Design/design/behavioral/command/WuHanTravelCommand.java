package com.atguigu.design.behavioral.command;

/**
 * 武汉出（旅）差（游）命令
 */
public class WuHanTravelCommand implements Command{

    private LeiReceiver receiver = new LeiReceiver();

    @Override
    public void execute() {
        System.out.println("我要出（旅）差（游）....");
        receiver.travel();
    }
}
