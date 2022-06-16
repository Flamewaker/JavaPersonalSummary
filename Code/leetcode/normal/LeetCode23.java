package com.todd.leetcode.normal;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/7/16 21:22
 * @description: 合并 k 个排序链表，返回合并后的排序链表。
 * 用容量为K的最小堆优先队列，把链表的头结点都放进去，然后出队当前优先队列中最小的，挂上链表，然后让出队的那个节点的下一个入队，再出。
 * 时间复杂度：考虑优先队列中的元素不超过 k 个，那么插入和删除的时间代价为 O(logk)，这里最多有 kn 个点，对于每个点都被插入删除各一次，故总的时间代价即渐进时间复杂度为 O(kn×logk)。
 * 空间复杂度：这里用了优先队列，优先队列中的元素不超过 k 个，故渐进空间复杂度为 O(k)。
 */
public class LeetCode23 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(-1);
        ListNode temp = head;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] == null) {
                continue;
            }
            pq.offer(lists[i]);
        }
        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            temp.next = cur;
            temp = temp.next;
            if (cur.next != null) {
                pq.offer(cur.next);
            }
        }
        return head.next;
    }
}
