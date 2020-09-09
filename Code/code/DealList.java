package com.todd.code;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/7/16 15:16
 * @description: TODO
 */
public class DealList {
    static class ListNode {
       int val;
       ListNode next;
       ListNode() {}
       ListNode(int val) { this.val = val; }
       ListNode(int val, ListNode next) {
           this.val = val;
           this.next = next;
       }
    }

    //input:1->2->3->4->5->6

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String list = sc.nextLine();
        String[] nums = list.split("->");
        ListNode head = new ListNode(0);
        ListNode temp = head;
        for (int i = 0; i < nums.length; i++) {
            temp.next = new ListNode(Integer.parseInt(nums[i]));
            temp = temp.next;
        }

        while (head.next != null) {
            System.out.println(head.next.val);
            head = head.next;
        }
    }

}
