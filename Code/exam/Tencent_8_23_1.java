package com.todd.exam;

import java.util.*;
/**
 * @author todd
 * @date 2020/8/23 19:25
 * @description: 给一个字符串str
 * 比如aabb 找到由其字母拼起来的字典序的第k个子序列
 * 超时 80%
 */
public class Tencent_8_23_1 {

    public static int count = 0;
    public static String ans;
    public static HashSet<String> set;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int k = sc.nextInt();
        char[] s = str.toCharArray();
        Arrays.sort(s);
        count = 0;
        ans = null;
        set = new HashSet<>();
        StringBuilder a = new StringBuilder();
        dfs(s, a, 0, s.length - 1, k);
        System.out.println(ans);
    }

    public static void dfs(char[] s, StringBuilder a, int step, int n, int k) {
        if (step > n || count >= k) {
            return;
        }
        for (int i = step; i <= n; i++) {
            a.append(s[i]);
            if (!set.contains(a.toString())) {
                set.add(a.toString());
                count++;
                if (count == k) {
                    ans = a.toString();
                    return;
                }
            } else if (count >= k){
                return;
            } else {
                a.deleteCharAt(a.length() - 1);
                continue;
            }
            dfs(s, a, i + 1, n, k);
            a.deleteCharAt(a.length() - 1);
        }
    }
}
