package com.todd.leetcode.hotandtop;

import java.util.HashMap;

/**
 * @author tongchengdong
 * @description LRU 缓存
 * @date 12:13 AM 2022/6/5
 */
public class LeetCode146 {
    class LRUCache {

        HashMap<Integer, Node> map;

        Node head;

        Node tail;

        int cap;

        class Node {
            int key;
            int value;
            Node next;
            Node prev;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
                this.next = null;
                this.prev = null;
            }
        }

        public LRUCache(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            head = null;
            tail = null;
        }

        public int get(int key) {
            Node curr = map.get(key);
            if (curr == null) {
                return -1;
            } else {
                remove(curr);
                appendHead(curr);
            }
            return curr.value;
        }

        public void put(int key, int value) {
            Node curr = map.get(key);
            if (curr == null) {
                Node newNode = new Node(key, value);
                if (map.size() >= cap) {
                    map.remove(tail.value);
                    remove(tail);
                }
                map.put(key, newNode);
                appendHead(newNode);
            } else {
                remove(curr);
                curr.value = value;
                appendHead(curr);
            }
        }

        private void remove(Node curr) {
            if (head == tail) {
                head = null;
                tail = null;
            } else if (curr == head) {
                head = head.next;
                curr.next = null;
                head.prev = null;
            } else if (curr == tail) {
                tail = tail.prev;
                curr.prev = null;
                tail.next = null;
            } else {
                curr.next.prev = curr.prev;
                curr.prev.next = curr.next;
                curr.next = null;
                curr.prev = null;
            }
        }

        private void appendHead(Node curr) {
            if (head == null) {
                head = curr;
                tail = curr;
            } else {
                curr.next = head;
                head.prev = curr;
                head = curr;
            }
        }
    }
}
