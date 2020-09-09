package com.todd.redo.offer;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author todd
 * @date 2020/8/25 9:14
 * @description: 剑指 Offer 37. 序列化二叉树
 */
public class Offer37 {
    // Encodes a tree to a single string.

    public String serialize(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        StringBuilder str = new StringBuilder();
        str.append("[");

        LinkedList<TreeNode> que = new LinkedList<>();
        que.offerLast(root);
        while (!que.isEmpty()) {
            TreeNode cur = que.pollFirst();
            if (cur != null) {
                str.append(cur.val);
                str.append(",");
                que.offerLast(cur.left);
                que.offerLast(cur.right);
            } else {
                str.append("null,");
            }
        }
        str.deleteCharAt(str.length() - 1);
        str.append("]");
        return str.toString();
    }

    // Decodes your encoded data to tree.

    public TreeNode deserialize(String data) {
        if ("[]".equals(data)) {
            return null;
        }
        String[] values = data.substring(1, data.length() - 1).split(",");
        LinkedList<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        queue.offerLast(root);
        int index = 1;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.pollFirst();
            if (!"null".equals(values[index])) {
                cur.left = new TreeNode(Integer.parseInt(values[index]));
                queue.offerLast(cur.left);
            }
            index++;
            if (!"null".equals(values[index])) {
                cur.right = new TreeNode(Integer.parseInt(values[index]));
                queue.offerLast(cur.right);
            }
            index++;
        }

        return root;
    }
}
