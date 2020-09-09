package com.todd.redo.nowcoder;

/**
 * @author todd
 * @date 2020/8/6 10:50
 * @description: 给了一个序列，让找出最长的“凸子序列”
 * 最长上升子序列，从后往前和从前往后各遍历一次。
 */
public class NC511 {
    public int mountainSequence (int[] numberList) {
        if (numberList.length == 0) {
            return 0;
        }
        int len = numberList.length;
        int[] up = new int[len];
        int[] down = new int[len];
        int ans = 0;
        for (int i = 0; i < len; i++) {
            up[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (numberList[i] > numberList[j]) {
                    up[i] = Math.max(up[i], up[j] + 1);
                }
            }
        }
        for (int i = len - 1; i >= 0; i--) {
            down[i] = 1;
            for (int j = i + 1; j < len; j++) {
                if (numberList[i] > numberList[j]) {
                    down[i] = Math.max(down[i], down[j] + 1);
                }
            }
        }
        for (int i = 0; i < len; i++) {
            ans = Math.max(ans, up[i] + down[i] - 1);
        }
        return ans;
    }
}
