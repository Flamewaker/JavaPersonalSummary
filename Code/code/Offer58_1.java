package com.todd.code;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
 * split(), trim(), strip(), join(), reverse()
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer58_1 {
    public String reverseWords(String s) {
        // 除去开头和末尾的空白字符
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }

    public String reverseWords2(String s) {
        s = s.trim();
        String[] a = s.split("\\s+"); //效率较低，可以直接用“ ”，但逻辑需要更改。
        StringBuffer sb = new StringBuffer();
        for (int i = a.length - 1; i >= 0; i--) {
            sb.append(a[i]);
            if(i != 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
