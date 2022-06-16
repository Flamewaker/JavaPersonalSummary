package com.todd.nowcoder;

import jdk.nashorn.internal.objects.NativeUint16Array;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/9/9 20:26
 * @description: 合并k个已排序的链表
 */
public class NC51 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (lists == null || lists.size() == 0) {
            return null;
        }
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                pq.add(lists.get(i));
            }
        }
        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            temp.next = cur;
            if (cur.next != null) {
                pq.add(cur.next);
            }
            temp = temp.next;
        }
        return head.next;
    }
}
