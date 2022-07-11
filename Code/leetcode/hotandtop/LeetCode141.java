package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 141. 环形链表
 * 整体思路：
 * 1. 慢指针移动两步，快指针移动一步，相遇时证明有环.
 * @date 6:30 PM 2022/5/22
 */
public class LeetCode141 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 1. 定义快慢指针
        ListNode slow = head;
        ListNode fast = head;
        // 2. 判断是否有环
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
