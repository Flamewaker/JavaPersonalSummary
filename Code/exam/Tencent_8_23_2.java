package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/23 19:25
 * @description: 删除链表第k个节点 80% Java输出挂
 */
public class Tencent_8_23_2 {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int value) {
            this.val = value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            temp.next = new ListNode(val);
            temp = temp.next;
        }
        if (k <= n && k > 0) {
            ListNode newHead = deleteKNode(head.next, k);
            temp = newHead;
            StringBuilder s = new StringBuilder();
            while (temp != null) {
                if (temp.next != null) {
                    s.append(temp.val);
                    s.append(" ");
                } else {
                    s.append(temp.val);
                }
                temp = temp.next;
            }
            System.out.println(s.toString());
        } else {
            temp = head.next;
            StringBuilder s = new StringBuilder();
            while (temp != null) {
                if (temp.next != null) {
                    s.append(temp.val);
                    s.append(" ");
                } else {
                    s.append(temp.val);
                }
                temp = temp.next;
            }
            System.out.println(s.toString());
        }
    }

    public static ListNode deleteKNode(ListNode head, int k) {
        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode temp = newHead;
        while (--k > 0) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return newHead.next;
    }
}
