package com.todd.redo.top100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author todd
 * @date 2020/8/7 8:33
 * @description: 14. 最长公共前缀
 */
public class LeetCode14 {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        Trie root = new Trie();
        for (int i = 0; i < strs.length ;i++) {
            if (strs[i].length() == 0) {
                return "";
            }
            root.insert(strs[i]);
        }
        return root.searchPrefix();
    }

    class Trie {
        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            int length = str.length();
            TrieNode cur = root;
            for (int i = 0; i < length; i++) {
                if (cur.getChild().containsKey(str.charAt(i))) {
                    cur = cur.getChild().get(str.charAt(i));
                } else {
                    TrieNode next = new TrieNode();
                    cur.getChild().put(str.charAt(i), next);
                    cur = next;
                }

            }
            cur.setEnd(true);
        }

        public String searchPrefix() {
            TrieNode cur = root;
            StringBuilder str = new StringBuilder();
            while (cur.getChild().keySet().size() == 1) {
                for (Map.Entry<Character, TrieNode> child : cur.getChild().entrySet()) {
                    str.append(child.getKey());
                    cur = child.getValue();
                }
                if (cur.isEnd()) {
                    break;
                }
            }
            return str.toString();
        }
    }

    class TrieNode {
        HashMap<Character, TrieNode> child;
        boolean isEnd;

        TrieNode() {
            this.child = new HashMap<>();
            this.isEnd = false;
        }

        public HashMap<Character, TrieNode> getChild() {
            return this.child;
        }

        public boolean isEnd() {
            return this.isEnd;
        }

        public void setEnd(boolean end) {
            this.isEnd = end;
        }
    }


}
