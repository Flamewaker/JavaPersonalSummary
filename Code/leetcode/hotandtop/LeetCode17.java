package com.todd.leetcode.hotandtop;

import java.util.*;

/**
 * @author tongchengdong
 * @description 17. 电话号码的字母组合
 * 整体思路：
 * 简单dfs + 回溯
 * 终止条件：index == digits.length()
 * @date 11:00 AM 2022/5/21
 */
public class LeetCode17 {

    public static HashMap<Character, List<Character>> map;

    static {
        map = new HashMap<>();
        map.put('1', new ArrayList<>());
        map.put('2', Arrays.asList('a', 'b', 'c'));
        map.put('3', Arrays.asList('d', 'e', 'f'));
        map.put('4', Arrays.asList('g', 'h', 'i'));
        map.put('5', Arrays.asList('j', 'k', 'l'));
        map.put('6', Arrays.asList('m', 'n', 'o'));
        map.put('7', Arrays.asList('p', 'q', 'r', 's'));
        map.put('8', Arrays.asList('t', 'u', 'v'));
        map.put('9', Arrays.asList('w', 'x', 'y', 'z'));
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        char[] chars = digits.toCharArray();
        List<String> res = new ArrayList<>();
        searchLetterCombinations(chars, 0, new StringBuilder(), res);
        return res;
    }

    private void searchLetterCombinations(char[] digitChars, int index, StringBuilder s, List<String> res) {
        if (index >= digitChars.length) {
            res.add(s.toString());
            return;
        }
        List<Character> characters = map.get(digitChars[index]);
        index++;
        for (Character character : characters) {
            s.append(character);
            searchLetterCombinations(digitChars, index, s, res);
            s.deleteCharAt(s.length() - 1);
        }
    }
}
