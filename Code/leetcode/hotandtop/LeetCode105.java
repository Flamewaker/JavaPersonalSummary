package com.todd.leetcode.hotandtop;

import com.todd.code.BinaryTree;

/**
 * @author tongchengdong
 * @description 105. 从前序与中序遍历序列构造二叉树
 * 模板题，记住
 * 根据一棵树的中序遍历与后序遍历构造二叉树。 反正必须要有中序才能构建，因为没有中序，你没办法确定树的形状。
 * 通常从先序序列或者后序序列开始，根据不同遍历方法的规律，选择合适的节点构造树。例如：先序序列的第一个节点是根节点，然后是它的左孩子，右孩子等等。
 * 从先序/后序序列中找到根节点，根据根节点将中序序列分为左子树和右子树。从中序序列中获得的信息是：如果当前子树为空（返回 None），否则继续构造子树。
 * @date 4:08 PM 2022/5/22
 */
public class LeetCode105 {
    /**
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     *
     * @param inorder
     * @param preorder
     * @return TreeNode
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(inorder, preorder, 0, 0, inorder.length - 1);
    }

    public TreeNode helper(int[] inorder, int[] preorder, int preStart, int inStart, int inEnd) {
        // 1.子树为空, 返回null
        if (inStart > inEnd) {
            return null;
        }

        // 2.选择前序遍历的第一个节点作为根节点
        int currentVal = preorder[preStart];
        TreeNode current = new TreeNode(currentVal);

        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == currentVal) {
                inIndex = i;
            }
        }

        // 3. 递归获得左右节点
        TreeNode left = helper(inorder, preorder, preStart + 1, inStart, inIndex - 1);
        TreeNode right = helper(inorder, preorder, preStart + (inIndex - inStart) + 1, inIndex + 1, inEnd);
        current.left = left;
        current.right = right;
        return current;
    }

    /**
     * 根据一棵树的后序遍历与中序遍历构造二叉树。
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return helper2(inorder, postorder, postorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode helper2(int[] inorder, int[] postorder, int postEnd, int inStart, int inEnd) {
        //子树为空, 返回null
        if (inStart > inEnd) {
            return null;
        }

        //选择后序遍历的最后一个节点作为根节点
        int currentVal = postorder[postEnd];
        TreeNode current = new TreeNode(currentVal);

        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == currentVal) {
                inIndex = i;
            }
        }

        //假设根节点在中序遍历中索引为 index。从 in_left 到 index - 1 属于左子树，从 index + 1 到 in_right 属于右子树。
        //根据后序遍历逻辑，递归创建右子树 helper(index + 1, in_right) 和左子树 helper(in_left, index - 1)。
        //返回根节点 root。
        //inEnd - inIndex 计算的是右子树节点的个数
        TreeNode left = helper2(inorder, postorder, postEnd - (inEnd - inIndex) - 1, inStart, inIndex - 1);
        TreeNode right = helper2(inorder, postorder, postEnd - 1, inIndex + 1, inEnd);
        current.left = left;
        current.right = right;
        return current;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

