package com.todd.redo.nowcoder;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author todd
 * @date 2020/9/9 20:39
 * @description: TODO
 */
public class NC80 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        if (pRoot == null) {
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode cur = queue.poll();
                level.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            ans.add(level);
        }
        return ans;

    }
}
