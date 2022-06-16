package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/3 9:09
 * @description: 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 * 简单题
 */
public class LeetCode83 {
    public class ListNode {
        int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.next.val == temp.val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return head;
    }
}
