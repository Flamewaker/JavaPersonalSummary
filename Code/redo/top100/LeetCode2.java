package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/6 14:55
 * @description: 2. 两数相加
 */
public class LeetCode2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + carry;
            carry = sum / 10;
            int cur = sum % 10;
            ListNode newNode = new ListNode(cur);
            temp.next = newNode;
            temp = temp.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + carry;
            carry = sum / 10;
            int cur = sum % 10;
            ListNode newNode = new ListNode(cur);
            temp.next = newNode;
            temp = temp.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int sum = l2.val + carry;
            carry = sum / 10;
            int cur = sum % 10;
            ListNode newNode = new ListNode(cur);
            temp.next = newNode;
            temp = temp.next;
            l2 = l2.next;
        }
        if (carry != 0) {
            ListNode newNode = new ListNode(carry);
            temp.next = newNode;
        }
        return head.next;
    }
}
