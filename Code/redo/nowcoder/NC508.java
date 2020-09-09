package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 9:03
 * @description: TODO
 * 给定两个长度相等的，由小写字母组成的字符串S1和S2，定义S1和S2的距离为两个字符串有多少个位置上的字母不相等。
 * 现在牛牛可以选定两个字母X1和X2，将S1中的所有字母X1均替换成X2。（X1和X2可以相同）
 * 牛牛希望知道执行一次替换之后，两个字符串的距离最少为多少。
 */
public class NC508 {
    public int GetMinDistance (String S1, String S2) {
        int ans = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            for (char d = 'a'; d <= 'z'; d++) {
                int dist = 0;
                for (int i = 0; i < S1.length(); i++) {
                    char cur = S1.charAt(i) == c ? d : S1.charAt(i);
                    if (cur != S2.charAt(i)) {
                        dist++;
                    }
                }
                ans = Math.min(ans, dist);
            }
        }
        return ans;
    }
}
