package com.todd;

/**
 * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
 * 思路：将所有的数每一位加起来，每一位的和映射到一个数组中，如果该位能被三整除，则为0，如果不能被三整除则为1.
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer56_2 {
    public int singleNumber(int[] nums) {
        int[] helper = new int[32];
        for (int num : nums) {
            for(int i = 0; i < 32; i++) {
//                helper[i] += (num & (1 << i)) != 0 ? 1 : 0;
                helper[i] += num & 1;
                num >>= 1;
            }
        }
        int ans = 0;
        int middle = 1;
        for(int i = 0; i < 32; i++) {
            if(helper[i] % 3 == 1) {
                ans += middle;
            }
            middle <<= 1;
        }
        return ans;
    }
}
