package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/16 9:10
 * @description: 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 * 归并排序和快速排序
 */
public class LeetCode148 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    /**
     * @param head
     * @return list
     * 归并排序
     */
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    public ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // fastP,slowP快慢双指针法，奇数个节点找到中点，偶数个节点找到中心左边的节点。
        // 注意fastP不能等于head，比如[1,2]的情况，slowP = 2(中心右边的节点)， fastP = null。
        // slowP.next = null，此时陷入了无限循环，所以需要找到清晰的边界。
        // 假设 1 作为起始下标。
        // 如果是奇数个。假设有(2n + 1)个节点，fastP = 2 + 2n = null。slow = 1 + n = mid。
        // 如果是偶数个。假设有 2n 个节点，fastP = 2 + 2(n - 1) = end。slow = 1 + (n - 1) = n = mid。

        ListNode fastP = head.next;
        ListNode slowP = head;
        while (fastP != null && fastP.next != null) {
            fastP = fastP.next.next;
            slowP = slowP.next;
        }
        ListNode a = mergeSort(slowP.next);
        slowP.next = null;
        ListNode b = mergeSort(head);
        return mergeList(a, b);
    }

    public ListNode mergeList(ListNode a, ListNode b) {
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                temp.next = a;
                a = a.next;
            } else {
                temp.next = b;
                b = b.next;
            }
            temp = temp.next;
        }
        if (a != null) {
            temp.next = a;
        }
        if (b != null) {
            temp.next = b;
        }
        return head.next;
    }

    /**
     * @param head
     * @return list
     * 快速排序
     */
    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        quickSort(newHead, null);
        return newHead.next;
    }

    // 注意这里是 (head, end)
    public ListNode quickSort(ListNode head, ListNode end) {
        if (head.next == end || head.next.next == end) {
            return head;
        }
        ListNode tempHead = new ListNode(-1);
        ListNode partition = head.next;
        ListNode current = head.next;
        ListNode tempSmall = tempHead;
        while (current.next != end) {
            if (current.next.val < partition.val) {
                tempSmall.next = current.next;
                tempSmall = tempSmall.next;
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        tempSmall.next = partition;
        head.next = tempHead.next;
        quickSort(head, partition);
        quickSort(partition, end);
        return head.next;
    }
}
