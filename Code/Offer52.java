package com.todd;

/**
 * 输入两个链表，找出它们的第一个公共节点。
 * 第一种方式是使用辅助栈，也就是从尾结点开始弹出，每次比较相同的节点，统计个数，空间复杂度为O(m+n)。
 * 第二种方式是使用双指针法，先统计链表的个数，在将长的链表指针往前走长出的个数，最后当遍历的两个都相同的时候即为所得到的公共节点。
 *
 * @Auther todd
 * @Date 2020/5/12
 */
public class Offer52 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (tempA != null) {
            tempA = tempA.next;
            lenA++;
        }
        while (tempB != null) {
            tempB = tempB.next;
            lenB++;
        }

        if (lenA >= lenB) {
            int step = lenA - lenB;
            while (step-- > 0) {
                headA = headA.next;
            }
        } else {
            int step = lenB - lenA;
            while (step-- > 0) {
                headB = headB.next;
            }
        }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}
