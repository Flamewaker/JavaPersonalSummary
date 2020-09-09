package com.todd.redo.offer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/8/25 7:27
 * @description: 剑指 Offer 32 - I. 从上到下打印二叉树
 */
public class Offer32_1 {
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        LinkedList<TreeNode> que = new LinkedList<>();
        que.offerLast(root);
        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            TreeNode cur = que.pollFirst();
            ans.add(cur.val);
            if (cur.left != null) {
                que.offerLast(cur.left);
            }
            if (cur.right != null) {
                que.offerLast(cur.right);
            }
        }
        int[] res = new int[ans.size()];
        for(int i = 0; i < ans.size(); i++) {
            res[i] = ans.get(i);
        }
        return res;

    }
}
