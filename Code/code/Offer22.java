package com.todd.code;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，
 * 即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，
 * 它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
 * 最开始的方法是使用的栈，后来采用快慢指针
 *
 * @Author todd
 * @Date 2020/4/16
 */

public class Offer22 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 使用栈进行记录
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        Deque<ListNode> stack = new LinkedList<>();
        while (head != null) {
            stack.offerLast(head);
            head = head.next;
        }
        if (stack.size() >= k) {
            while (--k > 0) {
                stack.pollLast();
            }
            return stack.pollLast();
        } else {
            return null;
        }
    }

    /**
     * 使用快慢指针
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd2(ListNode head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        int num = 0;
        ListNode slowNode = head;
        ListNode quickNode = head;
        while (quickNode != null) {
            if (num >= k) {
                slowNode = slowNode.next;
            }
            quickNode = quickNode.next;
            num++;
        }
        if (num < k) {
            return null;
        } else {
            return slowNode;
        }
    }
}
