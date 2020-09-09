package com.todd.redo.offer;

import javax.print.AttributeException;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/24 17:00
 * @description: 剑指 Offer 16. 数值的整数次方
 */
public class Offer17 {

    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode temp = newHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
        return newHead.next;
    }
}
