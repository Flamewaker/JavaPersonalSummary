package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 21. 合并两个有序链表
 * 整体思路：
 * 创建一个新的链表头，将节点按照顺序放入即可
 * @date 2:20 PM 2022/5/21
 */
public class LeetCode21 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return list1 != null ? list1 : list2;
        }
        ListNode newHead = new ListNode(-1);
        ListNode temp = newHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        if (list1 != null) {
            temp.next = list1;
        }
        if (list2 != null) {
            temp.next = list2;
        }
        return newHead.next;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
