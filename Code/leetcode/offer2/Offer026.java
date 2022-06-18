package com.todd.leetcode.offer2;

import java.util.List;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 026. 重排链表
 * 整体思路：
 * 1. 找到链表中点
 * 2. 反转链表
 * 3. 合并链表
 * @date 3:23 PM 2022/6/16
 */
public class Offer026 {

    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode midNode = findMidNode(head);
        ListNode nextHead = midNode.next;
        midNode.next = null;
        ListNode reversedHead = reverseList(nextHead);
        mergeList(head, reversedHead);
    }

    private ListNode findMidNode(ListNode head) {
        int len = 0;
        ListNode temp = head;
        while (temp != null) {
            len++;
            temp = temp.next;
        }
        temp = head;
        int midLen = len % 2 == 1 ? len / 2 + 1 : len / 2;
        while (--midLen > 0) {
            temp = temp.next;
        }
        return temp;
    }

    private ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private void mergeList(ListNode head1, ListNode head2) {
        ListNode l1 = head1;
        ListNode l2 = head2;
        while (l1 != null && l2 != null) {
            ListNode next1 = l1.next;
            ListNode next2 = l2.next;
            l1.next = l2;
            l2.next = next1;
            l1 = next1;
            l2 = next2;
        }
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}



