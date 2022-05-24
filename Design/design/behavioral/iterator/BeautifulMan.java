package com.atguigu.design.behavioral.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象聚合类
 */
public abstract class BeautifulMan {

    //不方便暴露给外界的集合。只允许外界获取而不可以操作
    private List<String> girlFriends = new ArrayList<>();

    void likeYou(String name){
        girlFriends.add(name);
    };
    void sayBye(String name){
        girlFriends.remove(name);
    };

    /**
     * 获取迭代器
     * @return
     */
    public Itr getIterator(){
        return new Iterator();
    }

    /**
     * 具体迭代器
     */
    class Iterator implements Itr{

       private int cursor = 0; //当前指针


       public boolean hasNext(){
           return cursor < girlFriends.size();
       }

       public String next(){
           //第一次调用返回第一个数据
           //下一次再调用自动访问下一个数据
           String s = girlFriends.get(cursor);
           cursor++;
           return s;
       }

        @Override
        public String firstLove() {
            return girlFriends.get(0);
        }

        @Override
        public String current() {
            return girlFriends.get(girlFriends.size()-1);
        }
    }


    /**
     * 抽象迭代器，写在外部该怎么写？
     */
    interface Itr {
        //有没有下一个
        boolean hasNext();
        //返回下一个
        String next();

        //返回初恋（第一个）
        String firstLove();

        //返回现任（最后一个女朋友）
        String current();
    }

}
