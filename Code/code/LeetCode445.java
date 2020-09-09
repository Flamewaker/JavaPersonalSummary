package com.todd.code;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 * 1. 链表相加(两数相加),栈实现。2.翻转链表模板
 *
 * @Author todd
 * @Date 2020/4/14
 */

public class LeetCode445 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 栈实现
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        Deque<Integer> stack1 = new LinkedList<>();
        Deque<Integer> stack2 = new LinkedList<>();
        ListNode head1 = l1;
        ListNode head2 = l2;
        while (head1 != null) {
            stack1.offerLast(head1.val);
            head1 = head1.next;
        }
        while (head2 != null) {
            stack2.offerLast(head2.val);
            head2 = head2.next;
        }
        int carry = 0;
        ListNode head = null;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry > 0) {
            int sum = carry;
            sum += stack1.isEmpty() ? 0 : stack1.pollLast();
            sum += stack2.isEmpty() ? 0 : stack2.pollLast();
            carry = sum / 10;
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = head;
            head = newNode;
        }
        return head;
    }

    /**
     * 翻转链表实现
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode node1 = reverseList(l1);
        ListNode node2 = reverseList(l2);
        int carry = 0;
        ListNode head = null;
        while (node1 != null || node2 != null || carry > 0) {
            int sum = carry;
            sum += node1 == null ? 0 : node1.val;
            sum += node2 == null ? 0 : node2.val;
            carry = sum / 10;
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = head;
            head = newNode;
            if (node1 != null) {
                node1 = node1.next;
            }
            if (node2 != null) {
                node2 = node2.next;
            }

        }
        return head;
    }

    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

}
