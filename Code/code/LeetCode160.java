package com.todd.code;

/**
 * @author todd
 * @date 2020/7/16 21:04
 * @description: 编写一个程序，找到两个单链表相交的起始节点。
 */
public class LeetCode160 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
           val = x;
           next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int lengthA = 0;
        int lengthB = 0;
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (tempA != null) {
            tempA = tempA.next;
            lengthA++;
        }

        while (tempB != null) {
            tempB = tempB.next;
            lengthB++;
        }

        if (lengthA == 0 || lengthB == 0) {
            return null;
        }

        if (lengthA > lengthB) {
            int step = lengthA - lengthB;
            while (step-- > 0) {
                headA = headA.next;
            }
        } else if (lengthB > lengthA) {
            int step = lengthB - lengthA;
            while (step-- > 0) {
                headB = headB.next;
            }
        }
        while (headA !=null && headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}
