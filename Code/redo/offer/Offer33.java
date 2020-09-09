package com.todd.redo.offer;

/**
 * @author todd
 * @date 2020/8/25 7:40
 * @description: 剑指 Offer 33. 二叉搜索树的后序遍历序列
 */
public class Offer33 {
    public boolean verifyPostorder(int[] postorder) {
        if(postorder == null) {
            return false;
        }
        if(postorder.length == 0){
            return true;
        }
        return verifySequenceOfBST(postorder, 0, postorder.length - 1);
    }

    public boolean verifySequenceOfBST(int[] seq, int left, int right){
        if(left >= right) {
            return true;
        }
        int i = left;
        while(seq[i] < seq[right]){
            i++;
        }
        int mid = i;
        while (seq[i] > seq[right]){
            i++;
        }
        return i == right && verifySequenceOfBST(seq, left, mid - 1) && verifySequenceOfBST(seq, mid, right - 1);
    }
}
