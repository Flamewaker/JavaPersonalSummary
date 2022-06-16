package com.todd.nowcoder;

import com.sun.deploy.util.LinkMouseListener;

/**
 * @author todd
 * @date 2020/9/3 17:18
 * @description: 输入一个链表，反转链表后，输出新链表的表头。
 */
public class NC78 {

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode ReverseList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
