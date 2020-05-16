package com.todd;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

/**
 * 给定一棵二叉搜索树，请找出其中第k大的节点。
 * 思路：利用二叉树的中序遍历对树进行遍历（由于是求第K大节点，因此左右子树的遍历反过来）。非递归实现，很重要。中序遍历先访问左子树，再输出根节点，再访问右子树。
 * 非递归中序遍历解释：中序遍历的非递归实现使用一个栈来实现，首先设置当前节点为根节点，将根节点及以下的最左边的所有节点入栈。
 * 当遍历完后，弹出处于栈顶的节点，输出。设置当前节点为弹出节点的右节点，此时，当前节点的左子树已经遍历完毕，需要遍历当前节点的右子树。循环继续。
 * 循环的条件，当前节点不为null或栈不为空
 * 总结：
 * 递归算法(常用于树的问题)：
 * 递归前：if判断递归是否要执行，一般都类似于if(condition){ 递归函数 }，剪枝作用
 * 递归中：判断递归如何要停下来，一般都类似于if(root==null){ return... }，触底反弹，进入下一个递归方法
 * 递归后：递归函数的结果如何处理：
 * 1、直接return，如寻找最近公共祖先节点，具有排他性
 * 2、收集递归结果处理，如找到二叉树深度，max(left, right)+1
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer54 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int count = 0;
    int ans = -1;

    //递归实现
    public int kthLargest(TreeNode root, int k) {
        count = k;
        kthLargest(root);
        return ans;
    }

    public void kthLargest(TreeNode root) {
        if (count < 1) {
            return;
        }
        if (root != null) {
            kthLargest(root.right);
            if (count == 1) {
                ans = root.val;
                count--;
                return;
            }
            count--;
            kthLargest(root.left);
        }
    }

    //中序遍历，非递归实现，很重要
    public int kthLargest2(TreeNode root, int k) {
        int count = 1;
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.addLast(root);
                root = root.right;
            }
            TreeNode pop = stack.pollLast();
            if (count == k) {
                return pop.val;
            }
            count++;
            root = pop.left;
        }
        return 0;
    }

}
