package com.todd.leetcode.offer2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author tongchengdong
 * @description TODO
 * @date 5:11 PM 2022/6/17
 */
public class Offer043 {
    class CBTInserter {
        TreeNode root;
        Queue<TreeNode> queue;

        public CBTInserter(TreeNode root) {
            this.root = root;
            this.queue = new LinkedList<>();
            queue.add(root);
            while(root.right != null){ // 当前结点有右儿子，其必不为p。当前结点无右儿子时，其必为p。
                queue.add(root.left);
                queue.add(root.right);
                queue.remove();
                root = queue.peek();
            }
            if(root.left != null) {
                queue.add(root.left); // 若为p有左无右情形，加上左儿子
            }

        }

        public int insert(int val) {
            TreeNode node = new TreeNode(val);
            queue.add(node); // 新结点可直接入队
            TreeNode p = queue.peek(); // 此时队首必为p
            if(p.left != null){ // p有左儿子，node作为p的右儿子插入，弹出p（使得下一个结点为p）
                p.right = node;
                queue.remove();
            }
            else {
                p.left = node; // p无左儿子，node作为p的左儿子插入，p保持不变
            }
            return p.val;
        }

        public TreeNode get_root() {
            return root;
        }
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
