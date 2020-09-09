package com.todd.code;

/**
 * @author todd
 * @date 2020/6/16 21:31
 * @description: TODO
 */
public class LeetCode236 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null  && right != null) {
            return root;
        }

        return left == null ? right : left;
    }
}
