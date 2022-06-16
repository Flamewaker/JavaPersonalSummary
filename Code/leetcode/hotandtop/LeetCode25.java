package com.todd.leetcode.hotandtop;

import java.util.List;

/**
 * @author tongchengdong
 * @description 25. K 个一组翻转链表
 * 整体思路：
 * 1. 利用指针每k个一组进行翻转。
 * 2. 递归进行翻转。
 * @date 3:14 PM 2022/6/5
 */
public class LeetCode25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int cnt = k;
        ListNode temp = head;
        // 1. 遍历到下一组k个节点的前一个节点
        while (--cnt > 0) {
            temp = temp.next;
            if (temp == null) {
                return head;
            }
        }
        ListNode next = temp.next;
        temp.next = null;
        // 2. 翻转当前k个节点
        ListNode newHead = reverseGroup(head);
        // 3. 递归翻转接下来的节点
        head.next = reverseKGroup(next, k);
        return newHead;
    }

    private ListNode reverseGroup(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    public ListNode reverseKGroupOptimized(ListNode head, int k) {
        if(k == 0) {
            return head;
        }
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }
        ListNode newHead = reverseGroupOptimized(head, k);
        head.next = reverseKGroup(tail, k);
        return newHead;

    }

    public ListNode reverseGroupOptimized(ListNode head, int k){
        ListNode prev = null;
        ListNode curr = head;
        ListNode next;
        while(curr != null && k-- > 0){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
