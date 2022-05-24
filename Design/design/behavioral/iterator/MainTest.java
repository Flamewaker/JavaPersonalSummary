package com.atguigu.design.behavioral.iterator;

public class MainTest {

    public static void main(String[] args) {

        MaYuCheng cheng = new MaYuCheng();

        cheng.likeYou("王刚");
        cheng.likeYou("李强");
        cheng.likeYou("赵根");


        BeautifulMan.Itr itr = cheng.getIterator();
        String s = itr.firstLove();
        System.out.println(s);

        String current = itr.current();
        System.out.println(current);

        System.out.println("=================");
        while (itr.hasNext()){
            String next = itr.next();
            System.out.println(next);
        }


    }
}
