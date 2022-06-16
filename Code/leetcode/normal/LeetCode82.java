package com.todd.leetcode.normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @author todd
 * @date 2020/9/10 16:42
 * @description: 82. 删除排序链表中的重复元素 II
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
 */
public class LeetCode82 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode temp = newHead;
        while (temp.next != null && temp.next.next != null) {
            if (temp.next.val == temp.next.next.val) {
                ListNode cur = temp.next;
                while (cur != null && cur.next != null && cur.val == cur.next.val) {
                    cur = cur.next;
                }
                temp.next = cur.next;
            } else {
                temp = temp.next;
            }

        }
        return newHead.next;
    }

    //无序链表的重复数删除
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) {
            return head;
        }
        Set<Integer> set = new HashSet<Integer>();
        ListNode temp = head;
        set.add(head.val);
        while (temp.next != null) {
            if (set.contains(temp.next.val)) {
                temp.next = temp.next.next;
            } else {
                set.add(temp.next.val);
                temp = temp.next;
            }
        }
        return head;
    }
}
