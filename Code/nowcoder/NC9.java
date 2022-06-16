package com.todd.nowcoder;

/**
 * @author todd
 * @date 2020/9/9 19:56
 * @description: 二叉树中是否存在和为指定sum的路径
 */
public class NC9 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }

    public boolean hasPathSum (TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            int value = sum - root.val;
            return value == 0 ? true : false;
        }
        boolean left = hasPathSum(root.left, sum - root.val);
        boolean right = hasPathSum(root.right, sum - root.val);
        return left || right;
    }

}
