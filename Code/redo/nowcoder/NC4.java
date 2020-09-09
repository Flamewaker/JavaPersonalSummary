package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/9/5 11:52
 * @description: 判断给定的链表中是否有环
 */
public class NC4 {
     class ListNode {
         int val;
         ListNode next;

         ListNode(int x) {
             val = x;
             next = null;
         }
     }
    public boolean hasCycle(ListNode head) {
         if (head == null) {
             return false;
         }
         ListNode slow = head;
         ListNode quick = head;
         while (quick != null && quick.next != null) {
             quick = quick.next.next;
             slow = slow.next;
             if (quick == slow) {
                 return true;
             }
         }
         return false;
    }
}
