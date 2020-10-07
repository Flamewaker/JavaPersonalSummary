package com.todd.redo.nowcoder;

import javafx.scene.chart.ValueAxis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author think
 * @date 2020/9/26
 */
public class NC93 {
    /**
     * lru design
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        LRU lru = new LRU();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (operators[i][0] == 1) {
                lru.set(operators[i][1], operators[i][2]);
            } else if (operators[i][0] == 2) {
                int value = lru.get(operators[i][1]);
                ans.add(value);
            }

        }
        return ans.stream().mapToInt(Integer::valueOf).toArray();
    }
}

class LRU {

    class Node {

        int key;
        int value;
        Node next;
        Node pre;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

    }

    private HashMap<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRU() {
        map = new HashMap<>();
    }


    public void set(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            node = new Node(key, value);
            appendHead(node);
            map.put(key, node);
        } else {
            node.value = value;
            removeNode(node);
            appendHead(node);
        }
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        } else {
            int val = node.value;
            removeNode(node);
            appendHead(node);
            return val;
        }
    }

    public void appendHead (Node node) {
        if (head == null && tail == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.pre = node;
            head = node;
        }
    }

    public void removeNode (Node node) {
        if (head == tail) {
            head = tail = null;
        } else if (head == node) {
            head = head.next;
            head.pre = null;
        } else if (tail == node) {
            tail = tail.pre;
            tail.next = null;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.next = null;
            node.pre = null;
        }
    }
}
