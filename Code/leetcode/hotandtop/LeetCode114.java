package com.todd.leetcode.hotandtop;

/**
 * @author tongchengdong
 * @description 114. 二叉树展开为链表
 * 整体思路：
 * 1. 按照先序遍历的顺序访问节点。
 * 2. 访问根节点后，访问左子树并将左子树转成链表放在右节点位置，再将右子树转成链表放在最后
 * @date 4:25 PM 2022/5/22
 */
public class LeetCode114 {
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        // 后序遍历二叉树，先将左子树拉直，再将右子树拉直，按照先序遍历合并链表
        flatten(root.left);
        flatten(root.right);
        TreeNode right = root.right;

        // 关键步骤
        root.right = root.left;
        root.left = null;

        TreeNode temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        temp.right = right;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
