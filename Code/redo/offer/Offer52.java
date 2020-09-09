package com.todd.redo.offer;



/**
 * @author todd
 * @date 2020/8/25 11:44
 * @description: 剑指 Offer 52. 两个链表的第一个公共节点
 */
public class Offer52 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int lenA = 0;
        int lenB = 0;
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (tempA != null) {
            lenA++;
            tempA = tempA.next;
        }
        while (tempB != null) {
            lenB++;
            tempB = tempB.next;
        }
        tempA = headA;
        tempB = headB;
        if (lenA > lenB) {
            int difference = lenA - lenB;
            while (difference-- > 0) {
                tempA = tempA.next;
            }
        } else {
            int difference = lenB - lenA;
            while (difference-- > 0) {
                tempB = tempB.next;
            }
        }
        while (tempA != null && tempB != null) {
            if (tempA == tempB) {
                return tempA;
            }
            tempA = tempA.next;
            tempB = tempB.next;
        }
        return null;
    }
}
