package com.todd.leetcode.hotandtop;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tongchengdong
 * @description 142. 环形链表 II
 * 1. 慢指针移动两步，快指针移动一步，相遇之后，快指针变成头指针，然后每次快慢指针各走一步直到相遇，相遇的节点就是入环节点。
 * 2. 设链表中环外部分的长度为 a。slow 指针进入环后，又走了 b 的距离与 fast 相遇， 环长 = (b + c) 。此时，fast 指针已经走完了环的 n 圈，
 * 因此它走过的总距离为 a+n(b+c)+b=a+(n+1)b+nc, 要走到环的起点时。任意时刻，fast 指针走过的距离都为 slow 指针的 2 倍，
 * 设 a+(n+1)b+nc = 2(a+b) == > a=c+(n−1)(b+c)，从相遇点到入环点的距离加上 n-1 圈的环长，恰好等于从链表头部到入环点的距离。
 * 因此，当发现 slow 与 fast 相遇时，我们再额外使用一个指针 ptr。起始，它指向链表头部；随后，它和 slow 每次向后移动一个位置。
 * 最终，它们会在入环点相遇。
 * @date 11:41 PM 2022/5/22
 */
public class LeetCode142 {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 1. 定义快慢指针
        ListNode slow = head;
        ListNode fast = head;

        // 2. 判断成环
        boolean flag = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                flag = true;
                break;
            }
        }

        // 3. 获取目标节点
        if (flag) {
            // 3.1 快指针指向头节点，然后每次快慢指针各走一步直到相遇，相遇的节点就是入环节点
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }
            return fast;
        } else {
            return null;
        }
    }

    public ListNode detectCycleBySet(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode temp = head;
        Set<ListNode> set = new HashSet<>();
        while(true){
            // 如果出现这种情况说明没有循环，返回null
            if(temp == null){
                return null;
            }
            // 如果set中包含该节点，说明有循环并且起点就是该节点
            if(set.contains(temp)){
                return temp;
            }else{
                // 如果不包含就加入到set中，后面进行判断
                set.add(temp);
            }
            temp = temp.next;
        }
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}
