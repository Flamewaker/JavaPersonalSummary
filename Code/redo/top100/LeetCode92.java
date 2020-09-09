package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/22 9:40
 * @description: 92. 反转链表 II
 */
public class LeetCode92 {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode temp = dummy;

        for (int i = 1; i < m; i++) {
            temp = temp.next;
        }

        ListNode tail = temp.next;
        ListNode curr = temp.next;
        ListNode prev = null;
        ListNode next = null;

        for (int i = m; i <= n; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        tail.next = curr;
        temp.next = prev;


        return dummy.next;
    }

}
