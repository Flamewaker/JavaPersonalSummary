package com.todd.leetcode.offer1;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author todd
 * @Date 2020/4/22
 */
public class Offer38 {
    /**
     * 62ms
     * 直接利用HashSet在回溯的时候去重
     */
    Set<String> lists = new HashSet<>();
    boolean[] visited;

    public String[] permutation(String s) {
        if (s == null || s.length() == 0) {
            return new String[]{};
        }
        visited = new boolean[s.length()];
        char[] str = s.toCharArray();
        dfs("", str, 0);
        return lists.toArray(new String[lists.size()]);
    }

    public void dfs(String str, char[] s, int len) {
        if (len == s.length) {
            lists.add(str);
            return;
        }
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(str + s[i], s, len + 1);
                visited[i] = false;
            }
        }
    }

    /**
     * 59ms
     * 直接利用HashSet在回溯的时候去重
     */
    public String[] permutation2(String s) {
        if (s == null || s.length() == 0) {
            return new String[0];
        }
        visited = new boolean[s.length()];
        char[] str = s.toCharArray();
        StringBuilder initStr = new StringBuilder();
        dfs2(initStr, str, 0);
        return lists.toArray(new String[lists.size()]);
    }

    public void dfs2(StringBuilder str, char[] s, int len) {
        if (len == s.length) {
            lists.add(str.toString());
        }
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                str.append(s[i]);
                dfs2(str, s, len + 1);
                str.deleteCharAt(str.length() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * 22ms
     * 直接利用备忘录，进行剪枝
     */
    List<String> ans = new LinkedList<>();

    public String[] permutation3(String s) {
        if (s == null || s.length() == 0) {
            return new String[0];
        }
        visited = new boolean[s.length()];
        char[] str = s.toCharArray();
        StringBuilder initStr = new StringBuilder();
        dfs3(initStr, str, 0);
        return ans.toArray(new String[ans.size()]);
    }

    public void dfs3(StringBuilder str, char[] s, int len) {
        if (len == s.length) {
            ans.add(str.toString());
        }
        HashSet<Character> book = new HashSet<>();
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (book.contains(s[i])) {
                    continue;
                }
                book.add(s[i]);
                visited[i] = true;
                str.append(s[i]);
                dfs3(str, s, len + 1);
                str.deleteCharAt(str.length() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * 20ms
     * 直接利用备忘录，进行剪枝
     */
    public void dfs4(StringBuilder str, char[] s, int len) {
        if (len == s.length) {
            ans.add(str.toString());
        }
        boolean[] used = new boolean[256];
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (used[s[i]]) {
                    continue;
                }
                used[s[i]] = true;
                visited[i] = true;
                str.append(s[i]);
                dfs4(str, s, len + 1);
                str.deleteCharAt(str.length() - 1);
                visited[i] = false;
            }
        }
    }


}
