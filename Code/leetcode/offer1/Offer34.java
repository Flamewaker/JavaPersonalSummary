package com.todd.leetcode.offer1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
 * 此题还是比较有代表性的。采用的方法是dfs + 回溯。路径的记录采用全局变量，路径随着遍历的过程进行回溯和更新。
 * 采用ArrayList比采用LinkedList和Deque的时间都短。
 * ArrayList Add: 12
 * LinkedList Add: 6
 * ArrayList Copy: 9386
 * LinkedList Copy: 26943
 * ArrayList Remove last: 2
 * LinkedList Remove last: 2
 * 从main函数的验证程序可以看出，在数据量不大的情况下，Add所占的时间不多，（不须像ArrayList那样去重新申请数组空间），
 * 但是在Copy过程中LinkedList所消耗的时间明显比ArrayList长。所以在需要对路径进行保存的题目中使用ArrayList。
 *
 * @Author todd
 * @Date 2020/4/22
 */
public class Offer34 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public ArrayList<Integer> path = new ArrayList<>();
    public List<List<Integer>> lists = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) {
            return lists;
        }
        findPath(root, sum);
        return lists;
    }

    public void findPath(TreeNode root, int sum) {
        path.add(root.val);
        int nextSum = sum - root.val;
        if (nextSum == 0 && root.left == null && root.right == null) {
            lists.add(new ArrayList<>(path));
        }
        if (root.left != null) {
            findPath(root.left, nextSum);
        }
        if (root.right != null) {
            findPath(root.right, nextSum);
        }
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list1.add(i);
        }
        long end1 = System.currentTimeMillis();
        long time1 = end1 - start1;
        System.out.println("ArrayList Add: " + time1);

        LinkedList<Integer> list2 = new LinkedList<>();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list2.add(i);
        }
        long end2 = System.currentTimeMillis();
        long time2 = end2 - start2;
        System.out.println("LinkedList Add: " + time2);

        LinkedList<ArrayList<Integer>> list3 = new LinkedList<>();
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            list3.add(new ArrayList<>(list1));
        }
        long end3 = System.currentTimeMillis();
        long time3 = end3 - start3;
        System.out.println("ArrayList Copy: " + time3);

        LinkedList<LinkedList<Integer>> list4 = new LinkedList<>();
        long start4 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            list4.add((LinkedList<Integer>) list2.clone());
        }
        long end4 = System.currentTimeMillis();
        long time4 = end4 - start4;
        System.out.println("LinkedList Copy: " + time4);

        long start5 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list1.remove(list1.size() - 1);
        }
        long end5 = System.currentTimeMillis();
        long time5 = end5 - start5;
        System.out.println("ArrayList Remove last: " + time5);

        long start6 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list2.pollLast();
        }
        long end6 = System.currentTimeMillis();
        long time6 = end6 - start6;
        System.out.println("LinkedList Remove last: " + time6);
    }
}
