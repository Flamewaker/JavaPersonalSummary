package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 11:02
 * @description: TODO
 * 众所周知，牛妹非常喜欢吃蛋糕。
 * 第一天牛妹吃掉蛋糕总数三分之一（向下取整）多一个，第二天又将剩下的蛋糕吃掉三分之一（向下取整）多一个，以后每天吃掉前一天剩下的三分之一（向下取整）多一个，
 * 到第n天准备吃的时候只剩下一个蛋糕。牛妹想知道第一天开始吃的时候蛋糕一共有多少呢？
 */
public class NC512 {
    public static void main(String[] args) {
        System.out.println(cakeNumber(4));
    }

    public static int cakeNumber (int n) {
        int ans = 1;
        while (n-- > 1) {
            ans = (ans * 3 + 3) / 2;
        }
        return ans;
    }

}
