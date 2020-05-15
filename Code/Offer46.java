package com.todd;

/**
 * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 *
 * @Auther todd
 * @Date 2020/5/15
 */
public class Offer46 {
    int counter = 0;

    public int translateNum(int num) {

        helper(num + "", 0);
        return counter;
    }

    public void helper(String num, int pos) {
        if (pos == num.length()) {
            counter++;
            return;
        }
        helper(num, pos + 1);
        if (pos + 1 < num.length() && ((num.charAt(pos) == '1') || (num.charAt(pos) == '2' && num.charAt(pos + 1) <= '5'))) {
            helper(num, pos + 2);
        }

    }

    public int translateNum2(int num) {
        String s = num + "";
        int[] dp = new int[s.length() + 2];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            char ch = s.charAt(i - 2);
            if ((i + 1 < dp.length) && (ch == '1' || (ch == '2' && s.charAt(i - 1) <= '5'))) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[dp.length - 1];
     }
}
