package com.todd.redo.top100;

/**
 * @author todd
 * @date 2020/8/7 10:18
 * @description: 19. 删除链表的倒数第N个节点
 */
public class LeetCode19 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5};
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        for (int i = 0; i < 5; i++) {
            ListNode newNode = new ListNode(nums[i]);
            temp.next = newNode;
            temp = temp.next;
        }
        ListNode ans = removeNthFromEnd(head.next, 2);
        while (ans != null) {
            System.out.print(ans.val + " ");
            ans = ans.next;
        }
        System.out.println();
    }
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode quickNode = newHead.next;
        ListNode slowNode = newHead;
        while (n-- > 0) {
            if (quickNode == null) {
                return newHead.next;
            }
            quickNode = quickNode.next;
        }
        while (quickNode != null) {
            quickNode = quickNode.next;
            slowNode = slowNode.next;
        }
        slowNode.next = slowNode.next.next;
        return newHead.next;
    }
}
