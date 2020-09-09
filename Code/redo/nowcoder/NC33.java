package com.todd.redo.nowcoder;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/9/5 12:05
 * @description: 将两个有序的链表合并为一个新链表，要求新的链表是通过拼接两个链表的节点来生成的。
 */
public class NC33 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    public ListNode mergeTwoLists (ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode head = new ListNode(-1);
        ListNode temp = head;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }

        if (l1 != null) {
            temp.next = l1;
        }

        if (l2 != null) {
            temp.next = l2;
        }

        return head.next;
    }
}
