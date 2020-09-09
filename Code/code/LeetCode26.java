package com.todd.code;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author todd
 * @date 2020/6/3 20:37
 * @description: 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。重要*******
 * 未通过
 * 1. 每次 O(K) 比较 K个指针求 min, 时间复杂度：O(NK)
 * 2. 使用小根堆对 1 进行优化，每次 O(logK) 比较 K个指针求 min, 时间复杂度：O(NlogK)
 * 3. 逐一合并两条链表， 两两合并优化，先合并短的再合并长的。时间复杂度分析：K条链表的总结点数是N，平均每条链表有 N/K 个节点，因此合并两条链表的时间复杂度是 O(N/K)。从 K条链表开始两两合并成 1 条链表，因此每条链表都会被合并 logK 次，因此 K 条链表会被合并 K * logK次，因此总共的时间复杂度是 K*logK*N/K即 O（NlogK）。
 *
 */
public class LeetCode26 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (true) {
            ListNode minNode = null;
            int minPointer = -1;
            for (int i = 0; i < k; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (minNode == null || lists[i].val < minNode.val) {
                    minNode = lists[i];
                    minPointer = i;
                }
            }
            if (minPointer == -1) {
                break;
            }
            tail.next = minNode;
            tail = tail.next;
            lists[minPointer] = lists[minPointer].next;
        }
        return dummyHead.next;
    }

    public ListNode mergeKLists2(ListNode[] lists) {
        Queue<ListNode> pq = new PriorityQueue<>((v1, v2) -> v1.val - v2.val);
        for (ListNode node: lists) {
            if (node != null) {
                pq.offer(node);
            }
        }

        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (!pq.isEmpty()) {
            ListNode minNode = pq.poll();
            tail.next = minNode;
            tail = minNode;
            if (minNode.next != null) {
                pq.offer(minNode.next);
            }
        }

        return dummyHead.next;
    }

    private ListNode merge2Lists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        tail.next = l1 == null? l2: l1;

        return dummyHead.next;
    }

    public ListNode mergeKLists3(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        int k = lists.length;
        while (k > 1) {
            int idx = 0;
            for (int i = 0; i < k; i += 2) {
                if (i == k - 1) {
                    lists[idx++] = lists[i];
                } else {
                    lists[idx++] = merge2Lists(lists[i], lists[i + 1]);
                }
            }
            k = idx;
        }
        return lists[0];
    }


}
