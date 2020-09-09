package com.todd.exam;

import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/24 13:25
 * @description: AC
 * 在一张透明的纸上，用笔写下一个字符串。然后将纸翻面，请你判断正面和背面看到的字符串是否一样。 请注意，字符串在正反面看上去一样，必须要求每个字符是左右对称的，比如'W'字符是左右对称的，而'N'不是。
 */
public class T360_8_24_1 {
    public static void main(String[] args) {
        HashSet<Character> set = new HashSet();
        set.add('A');
        set.add('H');
        set.add('I');
        set.add('M');
        set.add('O');
        set.add('T');
        set.add('U');
        set.add('V');
        set.add('W');
        set.add('X');
        set.add('Y');
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            if (judge(str, set)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public static boolean judge(String str, HashSet<Character> set) {
        if (str.length() == 0) {
            return true;
        }
        char[] s = str.toCharArray();
        int left = 0;
        int right = s.length - 1;
        while (left <= right) {
            if (s[left] == s[right] && set.contains(s[left])) {
                right--;
                left++;
            } else {
                return false;
            }
        }
        return true;
    }
}
