package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 021. 删除链表的倒数第 n 个结点
 * @date 11:34 AM 2022/6/16
 */
public class Offer021 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        // 1. 创建slow和quick节点, start和end间隔n个节点
        ListNode newHead = new ListNode();
        newHead.next = head;
        ListNode slow = newHead;
        ListNode quick = newHead.next;
        while (n-- > 0) {
            if (quick == null) {
                return head;
            }
            quick = quick.next;
        }
        while (quick != null) {
            quick = quick.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return newHead.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

