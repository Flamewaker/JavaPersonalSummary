package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/24 19:57
 * @description: 剑指 Offer 22. 链表中倒数第k个节点
 */
public class Offer22 {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;
        while (k-- > 0 && fast != null) {
            fast = fast.next;
        }
        if (k > 0) {
            return null;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
