package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 11:03
 * @description: 剑指 Offer 46. 把数字翻译成字符串
 */
public class Offer46 {
    public int translateNum(int num) {
        String str = String.valueOf(num);
        int[] dp = new int[str.length() + 1];
        dp[0] = 1;
        for (int i = 0; i < str.length(); i++) {
            if ((i >= 1) && (str.charAt(i - 1) == '1' || str.charAt(i - 1) == '2' && str.charAt(i) <= '5')) {
                dp[i + 1] = dp[i] + dp[i - 1];
            } else {
                dp[i + 1] = dp[i];
            }
        }
        return dp[str.length()];
    }
}
