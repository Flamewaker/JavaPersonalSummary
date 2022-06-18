package com.todd.leetcode.offer2;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 025. 链表中的两数相加
 * 整体思路：
 * 1. 将两个链表的节点都推入栈中，然后不断出栈，计算每个位置的值和进位，串连成一个新的链表
 * 2. 反转链表后，计算链表之和
 * @date 3:18 PM 2022/6/16
 */
public class Offer025 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node1 = reverseList(l1);
        ListNode node2 = reverseList(l2);
        int carry = 0;
        ListNode head = null;
        while(node1 != null || node2 != null || carry > 0){
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

    public ListNode reverseList(ListNode head){
        ListNode cur = head;
        ListNode pre = null;
        ListNode next = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public ListNode addTwoNumbersStack(ListNode l1, ListNode l2) {
        Deque<Integer> stack1 = new LinkedList<>();
        Deque<Integer> stack2 = new LinkedList<>();
        ListNode head1 = l1;
        ListNode head2 = l2;
        while(head1 != null){
            stack1.offerLast(head1.val);
            head1 = head1.next;
        }
        while(head2 != null){
            stack2.offerLast(head2.val);
            head2 = head2.next;
        }
        int carry = 0;
        ListNode head = null;
        while(!stack1.isEmpty() || !stack2.isEmpty() || carry > 0){
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


    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
