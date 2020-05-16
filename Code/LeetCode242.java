package com.todd;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 思路 ： 可以只利用一个长度为 26 的字符数组，将出现在字符串 s 里的字符个数加 1，而出现在字符串 t 里的字符个数减 1，最后判断每个小写字母的个数是否都为 0。
 * @Author todd
 * @Date 2020/5/15
 */
public class LeetCode242 {
    public boolean isAnagram(String s, String t) {
        int num[] = new int[26];
        if(s.length() != t.length()){
            return false;
        }
        for(int i = 0; i < s.length(); i++){
            num[s.charAt(i) - 'a']++;
            num[t.charAt(i) - 'a']--;
        }
        for(int i = 0; i < 26; i++){
            if(num[i] != 0){
                return false;
            }
        }
        return true;
    }
}
