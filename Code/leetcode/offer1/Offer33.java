package com.todd.leetcode.offer1;

/**
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
 * 二叉搜索树的后序遍历结果，left <- right <- root, 判断是否可以将最后一个值前面的部分分成俩部分，一部分小于最后一个值，一部分大于最后一个值。
 * 递归的终止条件是只剩下一个值。返回true的条件是将整个数组遍历完毕，并且分成的每个部分都符合后序遍历的顺序。
 *
 * @Author todd
 * @Date 2020/4/22
 */
public class Offer33 {
    public boolean verifyPostorder(int[] postorder) {
        if (postorder == null) {
            return false;
        }
        if (postorder.length == 0) {
            return true;
        }
        return verifySequenceOfBST(postorder, 0, postorder.length - 1);
    }

    public boolean verifySequenceOfBST(int[] seq, int left, int right) {
        if (left >= right) {
            return true;
        }
        int i = left;
        while (seq[i] < seq[right]) {
            i++;
        }
        int mid = i;
        while (seq[i] > seq[right]) {
            i++;
        }
        return i == right && verifySequenceOfBST(seq, left, mid - 1) && verifySequenceOfBST(seq, mid, right - 1);
    }
}
