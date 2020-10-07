package com.todd.redo.offer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/20 16:45
 * @description: TODO
 */
public class Offer34 {

    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }
    public Deque<Integer> ans = new LinkedList<>();
    public List<List<Integer>> lists = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        findPath(root, sum);
        return lists;
    }

    public void findPath(TreeNode root, int sum){
        if(root == null) {
            return;
        }
        ans.offerLast(root.val);
        int nextSum = sum - root.val;
        if(nextSum == 0 && root.left == null && root.right == null) {
            lists.add(new LinkedList<>(ans));
        }
        findPath(root.left, nextSum);
        findPath(root.right, nextSum);
        ans.pollLast();
    }
}
