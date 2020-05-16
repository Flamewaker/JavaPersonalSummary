package com.todd;

/**
 * 正则表达式匹配，递归方法，可以使用dp方法
 *
 * @Author todd
 * @Date 2020/4/15
 */
public class Offer19 {
    public boolean isMatch(String s, String p) {
        if (s == null && p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        return match(str, pattern, 0, 0);
    }

    public boolean match(char[] s, char[] p, int i, int j) {
        if (i == s.length && j == p.length) {
            return true;
        }
        if (i != s.length && j == p.length) {
            return false;
        }
        if (j < p.length - 1 && p[j + 1] == '*') {
            if (i < s.length && (p[j] == s[i] || p[j] == '.')) {
                return match(s, p, i + 1, j) || match(s, p, i + 1, j + 2) || match(s, p, i, j + 2);
            } else {
                return match(s, p, i, j + 2);
            }
        }
        if (i < s.length && j < p.length && (p[j] == s[i] || p[j] == '.')) {
            return match(s, p, i + 1, j + 1);
        }
        return false;
    }

}
