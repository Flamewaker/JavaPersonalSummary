package com.todd.others;

import netscape.security.UserTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/5 17:02
 * @description: 前中后序遍历
 */
public class BinaryTree {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 从当前节点开始遍历：（当入栈时访问节点内容，则为前序遍历；出栈时访问，则为中序遍历）
     * 1. 若当前节点存在，就存入栈中，并访问左子树；
     * 2. 直到当前节点不存在，就出栈，并通过栈顶节点访问右子树；
     * 3. 不断重复12，直到当前节点不存在且栈空。
     * 只需要在入栈、出栈的时候，分别进行节点访问输出，即可分别得到前序、中序的非递归遍历代码！
     */

    public void preOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                ans.add(cur.val);
                cur = cur.left;
            }
            cur = stack.pollLast();
            cur = cur.right;
        }
    }

    public void inOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            ans.add(cur.val);
            cur = cur.right;
        }
    }


    /**
     * 从当前节点开始遍历：
     * 1. 若当前节点存在，就存入栈中，第一次访问，然后访问其左子树；
     * 2. 直到当前节点不存在，需要回退，这里有两种情况：
     *   1）从左子树回退，通过栈顶节点访问其右子树（取栈顶节点用，但不出栈）
     *   2）从右子树回退，这时需出栈，并取出栈节点做访问输出。（需要注意的是，输出完毕需要置当前节点为空，以便继续回退。具体可参考代码中的 cur = NULL）
     * 3. 不断重复12，直到当前节点不存在且栈空。
     */
    public void postOrder(TreeNode root, ArrayList<Integer> ans) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.peekLast();
            if (cur.right == null || cur.right == pre) {
                ans.add(cur.val);
                stack.pollLast();
                pre = cur;
                cur = null;
            } else {
                cur = cur.right;
            }
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            List<Integer> levelAns = new LinkedList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode cur = queue.pollFirst();
                levelAns.add(cur.val);
                if (cur.left != null) {
                    queue.offerLast(cur.left);
                }
                if (cur.right != null) {
                    queue.offerLast(cur.right);
                }
            }
            ans.add(levelAns);
        }
        return ans;
    }

}


