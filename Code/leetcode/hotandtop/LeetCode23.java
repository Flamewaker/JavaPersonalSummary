package com.todd.leetcode.hotandtop;


import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/8/7 11:25
 * @description: TODO
 */
public class LeetCode23 {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        int length = lists.length;
        ListNode newHead = new ListNode(-1);
        ListNode temp = newHead;
        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (int i = 0; i < length; i++) {
            if (lists[i] == null) {
                continue;
            }
            queue.add(lists[i]);
        }
        while (!queue.isEmpty()) {
            ListNode cur = queue.poll();
            temp.next = cur;
            if (cur.next != null) {
                queue.add(cur.next);
            }
            temp = temp.next;
        }
        return newHead.next;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
