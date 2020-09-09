package com.todd.code;

/**
 * @author todd
 * @date 2020/6/16 16:47
 * @description: 反转一个单链表。
 * 1. 三指针法。（逻辑思路）
 * 2. 双指针法。
 */
public class LeetCode206 {
     public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
     }

    public ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode curr = head;
        ListNode prev = null;
        ListNode next = head.next;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public ListNode reverseList2(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode curr = head;
        ListNode prev = null;
        while(curr != null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
