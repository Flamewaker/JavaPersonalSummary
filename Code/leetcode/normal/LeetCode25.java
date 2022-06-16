package com.todd.leetcode.normal;

/**
 * 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
 * k是一个正整数，它的值小于或等于链表的长度。
 * 在翻转链表的时候，可以借助三个指针：prev、curr、next，分别代表前一个节点、当前节点和下一个节点。将curr指向的下一节点保存到next指针；curr指向prev，一起前进一步；
 * 重复之前步骤，直到k个元素翻转完毕；当完成了局部的翻转后，prev就是最终的新的链表头，curr 指向了下一个要被处理的局部，而原来的头指针 head 成为了链表的尾巴。
 *
 * @Auther todd
 * @Date 2020/5/15
 */
public class LeetCode25 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k==0) {
            return head;
        }
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }
        ListNode newHead = reverseGroup(head, k);
        head.next = reverseKGroup(tail, k);
        return newHead;

    }

    public ListNode reverseGroup(ListNode head, int k){
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head.next;
        while(curr != null && k-- > 0){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }


}
