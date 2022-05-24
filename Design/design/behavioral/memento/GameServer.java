package com.atguigu.design.behavioral.memento;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏服务器
 * 管理者
 */
public class GameServer {

    //管理备忘录信息的
    Map<Integer,GameRecord> records = new HashMap<>();
    int i = 1;

    void add(GameRecord gameRecord){
        gameRecord.setId(i++);
        records.put(gameRecord.id,gameRecord);
    }

    LeiGamer getRecord(Integer id) throws Exception {
        GameRecord gameRecord = records.get(id);
        //获取到备忘录里面的内容以后还要逆转

        LeiGamer leiGamer = new LeiGamer();
//        leiGamer.setCoin(gameRecord.getCoin());

        //BeanUtils：工具类，属性对拷
        BeanUtils.copyProperties(leiGamer,gameRecord);

        return  leiGamer;
    }

}
