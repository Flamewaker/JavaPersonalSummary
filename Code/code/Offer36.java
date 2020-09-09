package com.todd.code;

import java.util.Deque;
import java.util.LinkedList;

/**
 * *************###########
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
 * 这个题很具有代表性，很值得深思。中序遍历中各个节点的指针指向，需要注意指针的生存情况。中序遍历，左<-中->右。
 * 按照中序遍历的顺序，将之后的每个节点的指针指向按顺序进行调整，注意保留首点和尾点。
 * pre:用来记录当前节点的上一个节点（最关键的就是这个pre了）
 * head：用来记录二叉树最左下角的最小值，也就是链表的头部
 *
 * @Author todd
 * @Date 2020/4/22
 */
public class Offer36 {
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    Node pre = null;
    Node head = null;

    public Node treeToDoublyList1(Node root) {
        if (root == null) {
            return null;
        }
        inOrder(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    public void inOrder(Node cur) {
        if (cur == null) {
            return;
        }
        inOrder(cur.left);
        if (pre == null) {
            head = cur;
        } else {
            pre.right = cur;
            cur.left = pre;
        }
        pre = cur;
        inOrder(cur.right);
    }

    /**
     * 非递归方法
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList2(Node root) {
        if (root == null) {
            return null;
        }
        Deque<Node> stack = new LinkedList<>();
        Node cur = root;
        Node pre = null, head = null, tail = null;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.offerLast(cur);
                //遍历左节点
                cur = cur.left;
            }
            cur = stack.pop();
            tail = cur;
            if (pre == null) {
                head = cur;
            } else {
                pre.right = cur;
                cur.left = pre;
            }
            pre = cur;
            //遍历右节点
            cur = cur.right;
        }
        tail.right = head;
        head.left = tail;
        return head;
    }

}
