package com.todd.design.creatation.prototype;

import java.util.HashMap;
import java.util.Map;

public class NewMybatis {

    //缓存user.序列化和反序列化-深克隆
    private Map<String,User> userCache = new HashMap<>();

    /**
     * 从数据库查数据
     * @return
     */
    public User getUser(String username) throws Exception {
        User user = null;
        //缓存中没有
        if(!userCache.containsKey(username)){
            //查询数据库
            user = getUserFromDb(username);
        } else {
            //从缓存中直接拿，脏缓存问题
            //原型已经拿到，但是不能直接给。（本人）
            user = userCache.get(username);
            System.out.println("从缓存中拿到的是："+user);
            //从这个对象快速得到一个克隆体（克隆人）== 原型模式
            user = (User) user.clone();
        }

        return user;
    }

    private User getUserFromDb(String username) throws Exception{
        System.out.println("从数据库查到："+username);
        User user = new User();
        user.setUsername(username);
        user.setAge(18);
        //给缓存中放一个clone
        userCache.put(username, (User) user.clone());
        return user;
    }


}
