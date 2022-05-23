package com.todd.leetcode.hotandtop;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author tongchengdong
 * @description 234. 回文链表
 * 回文指的的是整个链表正读倒读都一样, 两种较好的实现方式参考
 * @date 10:18 AM 2022/5/23
 */
public class LeetCode234 {
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        int len = 0;
        ListNode temp = head;
        LinkedList<Integer> stack = new LinkedList<>();
        // 1. 统计链表中的节点个数
        while (temp != null) {
            len++;
            temp = temp.next;
        }
        // 2. 将前一半的链表节点入栈, 并记录下一个遍历节点
        temp = head;
        for (int i = 0; i < len / 2; i++) {
            stack.push(temp.val);
            temp = temp.next;
        }
        // 2.1 当链表节点个数为奇数时，跳过中间节点
        if (len % 2 != 0) {
            temp = temp.next;
        }
        // 3 比较回文链表
        while (temp != null) {
            if (temp.val != stack.pop()) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    public boolean isPalindromeOptimized(ListNode head) {
        //如果链表为空，直接返回true，默认就是回文
        if(head == null){
            return true;
        }
        //定义一个指针，指向链表的中间节点。利用快慢指针来定位
        //将链表分为前后两个链表
        ListNode firstTail = getFirstListNodeTail(head);
        //定义一个指针，指向后一个反转过链表的头节点
        //注意这里的两个链表经过反转，断开连接了。
        ListNode secondHead = reverse(firstTail.next);
        //定义一个指针指向前一个链表的头节点
        ListNode p1 = head;
        //定义一个指针，指向后一个链表的头节点
        ListNode p2 = secondHead;
        //当后一个链表不指向空时，就继续比较两个前后链表的节点值
        while(p2 != null){
            //如果两个链表的节点值不同，说明不是回文，就直接返回
            if(p1.val != p2.val){
                return false;
            }
            //将前后链表的节点指针同时向后移动一位，继续比较
            p1 = p1.next;
            p2 = p2.next;
        }
        //将结果返回
        return true;
    }

    /**
     * 返回中间节点模板
     */
    public ListNode getFirstListNodeTail(ListNode head){
        //定义一个快指针和一个慢指针
        ListNode fast = head;
        ListNode slow = head;
        //只要快指针不到末尾，就继续循环
        //当链表为偶数的时候取的是第一个节点，为中间数。
        //注意区分条件；fast.next !=null && fast.next.next !=null这个在偶数下取的第二个节点
        while(fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        //当快指针到达末尾时，此时慢指针移动的位置，就是中间节点的位置
        return slow;
    }

    /**
     * 反转链表模板
     */
    public ListNode reverse(ListNode head){
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
