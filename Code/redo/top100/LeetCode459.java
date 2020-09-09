package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/24 15:19
 * @description: 459. 重复的子字符串
 * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
 */
public class LeetCode459 {
    public boolean repeatedSubstringPattern(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.length() % i == 0) {
                String str = s.substring(0, i);
                boolean flag = true;
                for (int j = i; j + i <= s.length(); j += i) {
                    if (!s.substring(j, j + i).equals(str)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }
}
