package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 148. 排序链表 (*)
 * 用归并排序的思路，先不断分割，直到每个子区间只有一个节点位置，然后开始合并。
 * 复杂度：时间复杂度O(nlogn)，和归并排序的复杂度一样。空间复杂度O(logn)，递归的栈空间。
 * @date 12:09 AM 2022/5/23
 */
public class LeetCode148 {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode fastP = head.next;
        ListNode slowP = head;
        while (fastP != null && fastP.next != null) {
            fastP = fastP.next.next;
            slowP = slowP.next;
        }
        ListNode a = sortList(slowP.next);
        slowP.next = null;
        ListNode b = sortList(head);
        return mergeList(a, b);
    }

    private ListNode mergeList(ListNode a, ListNode b) {
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                temp.next = a;
                a = a.next;
            } else {
                temp.next = b;
                b = b.next;
            }
            temp = temp.next;
        }
        if (a != null) {
            temp.next = a;
        }
        if (b != null) {
            temp.next = b;
        }
        return head.next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
