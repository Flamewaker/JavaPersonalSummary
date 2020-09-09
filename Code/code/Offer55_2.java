package com.todd.code;

/**
 * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
 * 思路：对平衡二叉树进行后序遍历，函数判断的条件是当前节点的左子树的深度和右子树的深度之差小于1，在遍历每个节点的时候，记录其深度，当不是平衡树的时候，用-1来返回，进行提前剪枝。
 * 思路分析：从根节点出发递归左右子树，从叶子节点开始累加而获取深度，当某个子树的深度差 >1，说明不平衡，直接剪枝，停止递归否则获取深度。
 * 递归条件：左右子树深度差<=1则返回深度
 * 终止条件：左右子树深度差>1则说明不是平衡树，返回-1，或者遍历到叶子节点，返回0
 *
 * 时间复杂度：O(N) N为节点数，每个节点都需要递归操作且为O(1)
 * 空间复杂度：O(N) 节点数N递归所需空间
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer55_2 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isBalanced(TreeNode root) {
        if(root == null) {
            return true;
        }
        return recur(root) != -1;
    }

    public int recur(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = recur(root.left);
        if (left == -1) {
            return -1;
        }
        int right = recur(root.right);
        if (right == -1) {
            return -1;
        }
        return Math.abs(right - left) <= 1 ? Math.max(right, left) + 1 : -1;

    }
}
