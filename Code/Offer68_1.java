package com.todd;

import java.util.LinkedList;

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 最开始想的是使用BFS进行遍历，后来发现多余了。最开始傻乎乎写了返回的条件，后来发现不符合的情况全部返回。
 * 若 root.val < p.val，则p在root右子树中；
 * 若 root.val > p.val，则p在root左子树中；
 * 若 root.val = p.val，则p和 root 指向同一节点。
 * 循环过程 当指向节点cur为空时跳出
 * 当 p, q都在root的右子树中，则遍历至 root.right；否则，当 p, q都在 root的左子树中，则遍历至root.left；
 * 否则，说明找到了最近公共祖先，跳出。
 *
 * @Auther todd
 * @Date 2020/5/15
 */
public class Offer68_1 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);
        while (!que.isEmpty()) {
            TreeNode cur = que.poll();
            //(cur.val > p.val && cur.val < q.val) || (cur.val < p.val && cur.val > q.val) || cur.val == p.val || cur.val == q.val
            if (cur.val > p.val && cur.val > q.val) {
                que.add(cur.left);
            } else if (cur.val < p.val && cur.val < q.val) {
                que.add(cur.right);
            } else {
                return cur;
            }
        }
        return root;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.val > p.val && cur.val > q.val) {
                cur = cur.left;
            } else if (cur.val < p.val && cur.val < q.val) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return root;
    }
}
