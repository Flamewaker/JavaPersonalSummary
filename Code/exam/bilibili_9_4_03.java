package com.todd.exam;

/**
 * 平均碎片长度 （aaabbbaac） = length / 4 = 2 .
 */

public class bilibili_9_4_03 {
    public int GetFragment (String str) {
        if (str.length() == 0) {
            return 0;
        }
        int count = 1;
        int sum = str.length();
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != str.charAt(i - 1)) {
                count++;
            }
        }
        return sum / count;
    }
}
