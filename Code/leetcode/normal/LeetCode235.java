package com.todd.leetcode.normal;

import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/6/3 21:19
 * @description: 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 找到分叉结点。左边小，右边大。
 */
public class LeetCode235 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);
        while (!que.isEmpty()) {
            TreeNode cur = que.poll();
            //(cur.val > p.val && cur.val < q.val) || (cur.val < p.val && cur.val > q.val) || cur.val == p.val || cur.val == q.val
            if (cur.val > p.val && cur.val > q.val) {
                que.add(cur.left);
            } else if (cur.val < p.val && cur.val < q.val) {
                que.add(cur.right);
            } else {
                return cur;
            }
        }
        return root;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode next = root;
        while (true) {
            TreeNode cur = next;
            if (cur.val > p.val && cur.val > q.val) {
                next = cur.left;
            } else if (cur.val < p.val && cur.val < q.val) {
                next = cur.right;
            } else {
                return cur;
            }
        }
    }

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor3(root.right, p, q);
        }
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor3(root.left, p, q);
        }
        return root;
    }
}
