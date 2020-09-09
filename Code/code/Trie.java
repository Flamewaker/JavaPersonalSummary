package com.todd.code;

import java.time.temporal.Temporal;
import java.util.HashMap;

/**
 * @author todd
 * @date 2020/6/10 16:55
 * @description: LeetCode208 实现一颗字典树
 */
class Trie {

    private TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char a = word.charAt(i);
            if (cur.getContent().get(a) == null) {
                TrieNode p = new TrieNode();
                cur.getContent().put(a, p);
                cur = p;
            } else {
                cur = cur.getContent().get(a);
            }
        }
        cur.setEnd(true);
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char a = word.charAt(i);
            if ((cur = cur.getContent().get(a)) == null) {
                return false;
            }
        }
        return cur.isEnd();
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return false;
        }
        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char a = prefix.charAt(i);
            if ((cur = cur.getContent().get(a)) == null) {
                return false;
            }
        }
        return true;
    }

    public static class TrieNode {
        private boolean isEnd;
        private HashMap<Character, TrieNode> content;

        public TrieNode() {
            isEnd = false;
            content = new HashMap<>();
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public HashMap<Character, TrieNode> getContent() {
            return content;
        }

        public void setContent(HashMap<Character, TrieNode> content) {
            this.content = content;
        }
    }
}

