package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 242. 有效的字母异位词
 * @date 12:40 AM 2022/6/17
 */
public class Offer032 {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length() || s.equals(t)){
            return false;
        }
        int num[] = new int[26];
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
