package com.todd.code;

/**
 * @author todd
 * @date 2020/7/23 12:01
 * @description: 旋转链表
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class LeetCode61 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int length = 0;
        ListNode end = new ListNode(-1);
        end.next = head;
        while (end.next != null) {
            length++;
            end = end.next;
        }
        int rotate = length - k % length;
        if (rotate == length) {
            return head;
        }
        ListNode temp = new ListNode(-1);
        temp.next = head;
        while (temp.next != null && rotate-- > 0) {
            temp = temp.next;
        }
        ListNode newHead = temp.next;
        end.next = head;
        temp.next = null;
        return newHead;
    }
}
