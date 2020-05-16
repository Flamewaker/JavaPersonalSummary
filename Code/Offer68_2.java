package com.todd;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 1. 利用深度优先搜索记录路径，找链表的第一个公共节点。
 * 2. 递归查询两个节点p，q，如果某个节点等于节点p或节点q，则返回该节点的值给父节点。
 * 如果当前节点的左右子树分别包括p和q节点，那么这个节点必然是所求的解。
 * 如果当前节点有一个子树的返回值为p或q节点，则返回该值。（告诉父节点有一个节点存在其子树中）
 * 如果当前节点的两个子树返回值都为空，则返回空指针。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer68_2 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Deque<TreeNode> pathP = new LinkedList<>();
        Deque<TreeNode> pathQ = new LinkedList<>();
        findPath(root, p, pathP);
        findPath(root, q, pathQ);
        TreeNode curA = pathP.pollFirst();
        TreeNode curB = pathQ.pollLast();
        TreeNode ans = curA;
        while (curA == curB) {
            ans = curA;
            curA = pathP.pollFirst();
            curB = pathQ.pollFirst();
        }

        return ans;
    }

    public boolean findPath(TreeNode root, TreeNode p, Deque<TreeNode> path) {
        if (root == null) {
            return false;
        }

        path.offerLast(root);

        if(root.val == p.val) {
            return true;
        }

        boolean found = findPath(root.left, p, path) || findPath(root.right, p, path);

        if(!found) {
            path.pollLast();
        }

        return found;
    }


    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left == null? right : left;
    }

}
