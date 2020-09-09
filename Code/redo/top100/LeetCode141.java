package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/27 11:47
 * @description: 141. 环形链表
 */
public class LeetCode141 {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        boolean flag = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
