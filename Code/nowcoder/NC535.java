package com.todd.nowcoder;

/**
 * @author todd
 * @date 2020/9/3 14:36
 * @description: 牛牛的AC
 * 一年一度的春招就要到来了，牛牛为了备战春招，在家刷了很多道题，所以牛牛非常喜欢AC这两个字母。
 * 他现在有一个只包含A和C的字符串，你可以任意修改最多k个字符，让A变成C，或者C变成A。请问修改完之后，
 * 最长连续相同字符的长度是多少。
 *
 * 贪心区间问题，区间需要更改的情况为 a > k 并且 c > k的情况。
 */
public class NC535 {
    public int Solve (int k, String s) {
        int a = 0, c = 0, ans = 0;
        for (int l = 0, r = 0; r < s.length(); r++) {
            if (s.charAt(r) == 'A') {
                a++;
            } else {
                c++;
            }
            while (a > k && c > k) {
                if (s.charAt(l++) == 'A') {
                    a--;
                } else {
                    c--;
                }
            }
            ans = Math.max(ans, a + c);
        }
        return ans;
    }
}
