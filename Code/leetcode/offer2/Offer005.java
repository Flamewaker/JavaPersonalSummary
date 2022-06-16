package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 005. 单词长度的最大乘积
 * 定义一个masks数组来记录每个字符串的位掩码，对整个字符串数组进行位运算处理，对字符串数组的里的字符串进行两两按位与，
 * 若结果为0则说明两个字符串里没有相同字符，然后去更新答案
 * @date 2:15 PM 2022/6/13
 */
public class Offer005 {
    public int maxProduct(String[] words) {
        int n = words.length;
        // arr数组保存对应字母位为0则对应字母不存在，1则存在
        int[] arr = new int[n];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                arr[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 没有相同字符时与运算的结果为0
                if ((arr[i] & arr[j]) == 0) {
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }
}
