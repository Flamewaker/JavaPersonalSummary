package com.todd.redo.Star;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/8/12 11:16
 * @description: 实现一个LRU缓存，不使用LinkedHashMap
 */
public class LRUCache {
    class Node {
        int key;
        int val;
        Node pre;
        Node next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private HashMap<Integer, Node> hashmap;
    private Node head;
    private Node tail;
    private int capacity;

    public LRUCache(int capacity) {
        this.hashmap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = hashmap.get(key);
        if (node == null) {
            return -1;
        } else {
            int val = node.val;
            removeNode(node);
            appendHead(node);
            return val;
        }
    }

    public void put(int key, int val) {
        Node node = hashmap.get(key);
        if (node == null) {
            node = new Node(key, val);
            if (hashmap.size() >= capacity) {
                hashmap.remove(tail.key);
                removeNode(tail);
            }
            appendHead(node);
            hashmap.put(key, node);
        } else {
            node.val = val;
            removeNode(node);
            appendHead(node);
        }
    }

    private void appendHead(Node curr) {
        if (head == null && tail == null) {
            head = curr;
            tail = curr;
        } else {
            head.pre = curr;
            curr.next = head;
            head = curr;
        }
    }

    private void removeNode(Node curr) {
        if (head == tail) {
            head = tail = null;
        } else if (curr == head) {
            head = head.next;
        } else if (curr == tail) {
            tail = tail.pre;
        } else {
            curr.next.pre = curr.pre;
            curr.pre.next = curr.next;
        }
        curr.pre = null;
        curr.next = null;
    }
}
