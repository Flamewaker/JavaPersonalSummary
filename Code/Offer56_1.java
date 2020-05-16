package com.todd;

/**
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。
 * 请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 * 思路：先对所有数字进行一次异或，得到两个出现一次的数字的异或值。在异或结果中找到任意为 11 的位。
 * 根据这一位对所有的数字进行分组。在每个组内进行异或操作，得到两个数字。
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer56_1 {
    public int[] singleNumbers(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum ^= nums[i];
        }
        //-sum = sum 取反码 + 1，所以sum&(-sum)，得到sum最后一位的1。（原式：sum&(sum^(sum-1))）
        int mask = sum & (-sum);

        //获得k中最低位的1
//        int mask = 1;
//        while((sum & mask) == 0) {
//            mask <<= 1;
//        }


        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & mask) == 0) {
                ans[0] ^= nums[i];
            } else {
                ans[1] ^= nums[i];
            }
        }
        return ans;
    }
}
