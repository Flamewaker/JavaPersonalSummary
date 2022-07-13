package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/24 10:57
 * @description: 86. 分隔链表
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 * 用两个链表,一个链表放小于x的节点,一个链表放大于等于x的节点, 最后,拼接这两个链表.
 */
public class LeetCode0086 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return head;
        }
        ListNode smallList = new ListNode(-1);
        ListNode smallTemp = smallList;
        ListNode largeList = new ListNode(-1);
        ListNode largeTemp = largeList;
        ListNode temp = head;
        while (temp != null) {
            if (temp.val < x) {
                smallTemp.next = temp;
                smallTemp = smallTemp.next;
            } else {
                largeTemp.next = temp;
                largeTemp = largeTemp.next;
            }
            temp = temp.next;
        }
        smallTemp.next = largeList.next;
        largeTemp.next = null;
        return smallList.next;
    }
}
