package com.todd.leetcode.offer1;

/**
 * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
 * 先序遍历 + 递归
 * 初始条件
 * 1. A != null && B != null
 * 2. A.val = B.val 才继续向下遍历
 * 终止条件：
 * 1. B == null true
 * 2. A == null B!=null false
 * 3. A.val != B.val false;
 * 4. 先序遍历的左右俩边都要满足一致
 *
 * @Author todd
 * @Date 2020/4/17
 */
public class Offer26 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        boolean result = false;
        if (A != null && B != null) {
            if (A.val == B.val) {
                result = hasStructure(A, B);
            }
            if (!result) {
                result = isSubStructure(A.left, B);
            }
            if (!result) {
                result = isSubStructure(A.right, B);
            }
        }
        return result;
    }

    public boolean hasStructure(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null) {
            return false;
        }
        if (A.val != B.val) {
            return false;
        }
        return hasStructure(A.left, B.left) && hasStructure(A.right, B.right);
    }

    public boolean isSubStructure2(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        return judge(A, B) || isSubStructure2(A.left, B) || isSubStructure2(A.right, B);
    }

    private boolean judge(TreeNode A, TreeNode B) {
        return (A == null || B == null) ? (B == null) : (A.val == B.val) && judge(A.left, B.left) && judge(A.right, B.right);
    }

}
