package com.todd;

/**
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer5 {
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        for(char a : s.toCharArray()){
            if(a == ' '){
                res.append("%20");
            } else {
                res.append(a);
            }
        }
        return res.toString();
    }
}
