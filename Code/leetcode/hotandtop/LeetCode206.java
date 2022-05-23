package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 206. 反转链表
 * @date 11:03 AM 2022/5/23
 */
public class LeetCode206 {
    public ListNode reverseList(ListNode head) {
        //当链表的为空或只有一个节点的时候，直接返回。
        if(head == null || head.next == null){
            return head;
        }
        //定义一个指针赋予null值
        ListNode prev = null;
        //定义一个指针指向链表的第一个节点
        ListNode cur = head;
        //只要不移动到链表的尾部
        while(cur != null){
            //定义一个指针指向链表的第二个节点
            ListNode next = cur.next;
            //将第一个节点指向null，断开与第二个节点的连接
            cur.next = prev;
            //将第一个指针移到第二个节点
            prev = cur;
            //将第二个指针移动到第三个节点
            cur = next;
        }
        //此时第一个指针的位置就是反转后链表的头节点。
        return prev;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
