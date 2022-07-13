package com.todd.leetcode.normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author todd
 * @date 2020/6/17 12:19
 * @description: 最长公共前缀
 * 这题感觉最好的方式是使用前缀树。
 */
public class LeetCode0014 {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            //返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }

            }
        }
        return prefix;
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        Trie trie = new Trie();
        for (String str : strs) {
            if (str.length() == 0) {
                return "";
            }
            trie.insert(str);
            System.out.println(str);
        }
        return trie.searchPrefix();
    }

    class Trie {
        class TrieNode {
            HashMap<Character, TrieNode> child;
            boolean isEnd;

            public TrieNode() {
                this.child = new HashMap<>();
                this.isEnd = false;
            }

            public HashMap<Character, TrieNode> getChild() {
                return child;
            }

            public void setChild(HashMap<Character, TrieNode> child) {
                this.child = child;
            }

            public boolean isEnd() {
                return isEnd;
            }

            public void setEnd(boolean end) {
                isEnd = end;
            }
        }

        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            TrieNode cur = root;
            for (int i = 0; i < str.length(); i++) {
                char a = str.charAt(i);
                if (cur.getChild().get(a) == null) {
                    TrieNode p = new TrieNode();
                    cur.getChild().put(a, p);
                    cur = p;
                } else {
                    cur = cur.getChild().get(a);
                }
            }
            cur.setEnd(true);
        }

        public String searchPrefix() {
            TrieNode cur = root;
            StringBuilder str = new StringBuilder();
            while (cur.getChild().entrySet().size() == 1) {
                for (Map.Entry<Character, TrieNode> characterTrieNodeEntry : cur.getChild().entrySet()) {
                    str.append(characterTrieNodeEntry.getKey());
                    cur = characterTrieNodeEntry.getValue();
                }
                if (cur.isEnd()) {
                    return str.toString();
                }
            }
            return  str.toString();
        }
    }
}
