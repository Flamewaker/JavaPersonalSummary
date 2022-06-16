package com.todd.leetcode.normal;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/6/17 12:08
 * @description: TODO
 */
public class LeetCode19 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0) {
            return head;
        }

        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode quickNode = newHead.next;
        ListNode slowNode = newHead;
        int count = 0;
        while (quickNode != null) {
            if (count >= n) {
                slowNode = slowNode.next;
            }
            quickNode = quickNode.next;
            count++;
        }
        if (count >= n && slowNode.next != null){
            slowNode.next = slowNode.next.next;
            return newHead.next;
        }
        return head;
    }
}
