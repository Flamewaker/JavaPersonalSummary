package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 056. 二叉搜索树中两个节点之和
 * @date 3:42 PM 2022/6/20
 */
public class Offer056 {

    Set<Integer> set = new HashSet<>();

    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);
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
