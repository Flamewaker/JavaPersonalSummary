package com.todd.code;

/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"0123"及"-1E-16"都表示数值，
 * 但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
 * A[.[B]][E|eC]或.B[E|eC]。A为整数，C为整数，并且可以有正负号。B为整数但不能有正负号。
 * 至少需要有小数部分，也就是说必须在e之前出现数字。
 * 1. + - 必须出现在第一个位置或e后面第一个位置，否则错误。
 * 2. . e符号之后不能出现.，并且.只能出现一次，其他时候.不做限制。
 * 3. e e符号只能出现一次，并且e之前必须有数字. e之后只能有带符号整数。
 *
 * @author think
 * @Author todd
 * @Date 2020/4/16
 */
public class Offer20 {
    public boolean isNumber(String s) {
        if (s == null) {
            return false;
        }
        s = s.trim();
        int len = s.length();
        char[] str = s.toCharArray();
        boolean dotVisited = false;
        boolean eVisited = false;
        boolean numVisited = false;
        int i = 0;
        while (i < len) {
            if (str[i] == '+' || str[i] == '-') {
                if (i != 0 && str[i - 1] == 'e' && str[i - 1] == 'E') {
                    return false;
                }
            } else if (str[i] == '.') {
                if (dotVisited || eVisited) {
                    return false;
                }
                dotVisited = true;
            } else if (str[i] == 'e' || str[i] == 'E') {
                if (eVisited || !numVisited) {
                    return false;
                }
                eVisited = true;
                numVisited = false;
            } else if (str[i] >= '0' && str[i] <= '9') {
                numVisited = true;
            } else {
                return false;
            }
            i++;
        }
        return numVisited;
    }
}
