package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 19. 删除链表的倒数第 N 个结点 (*)
 * 整体思路：
 * 双指针（快慢指针）， slow 和 quick 间隔n个节点，遍历链表， 当 quick == null， 删除start对应的节点
 * @date 11:30 AM 2022/5/21
 */
public class LeetCode19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        // 1. 创建slow和quick节点, start和end间隔n个节点
        ListNode newHead = new ListNode();
        newHead.next = head;
        ListNode slow = newHead;
        ListNode quick = newHead.next;
        int index = 0;
        while (index++ < n) {
            quick = quick.next;
        }
        // 2. 遍历链表, 直到quick为null
        while (quick != null) {
            slow = slow.next;
            quick = quick.next;
        }
        // 3. 删除slow的下一个节点
        slow.next = slow.next.next;
        return newHead.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


}
