package com.todd.leetcode.offer2;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 004. 只出现一次的数字
 * 整体思路：
 * 1. 将整数的各个数位上的加起来，然后对3取余，若结果为0，则待求数字在该位上是0。
 * 2. 若结果为1，则待求数字在该位上是1。
 * @date 9:09 AM 2022/6/13
 */
public class Offer004 {
    public int singleNumber(int[] nums) {
        int[] count = new int[32];
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                count[i] += num & 1;
                num >>>= 1;
            }
        }
        int ans = 0, m = 3;
        for (int i = 0; i < 32; i++) {
            ans <<= 1;
            ans |= count[31 - i] % m;
        }
        return ans;
    }
}
