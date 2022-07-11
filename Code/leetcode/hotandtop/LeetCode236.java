package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 236. 二叉树的最近公共祖先
 * 整体思路：
 * 1. 搜索到对应的p和q节点，回溯到第一个公共祖先节点（当前节点下的搜索结果非空）。
 * @date 12:23 PM 2022/5/23
 */
public class LeetCode236 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // 1. 搜索到了指定节点，接下来需要回溯到第一个公共祖先节点
        if (root.val == p.val || root.val == q.val) {
            return root;
        }
        // 2. 左右子树搜索对应的节点
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 3. 当左右子树均能搜索到结果时，此时当前节点就是公共祖先节点
        if (left != null && right != null) {
            return root;
        }
        // 4. 其余情况将节点返回即可， 待回溯到公共祖先节点
        return left == null ? right : left;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }

    }

}
