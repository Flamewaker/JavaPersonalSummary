package com.todd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 * 先排序，再判断
 *
 * @Auther todd
 * @Date 2020/5/14
 */
public class Offer61 {
    public boolean isStraight(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        Arrays.sort(nums);
        int zeroNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                zeroNum++;
            }
        }
        int last = 0;
        int needNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if(last != 0 && nums[i] == last) {
                return false;
            } else if(last != 0 && nums[i] != last + 1) {
                needNum += nums[i] - last - 1;
            }
            last = nums[i];
        }
        return zeroNum >= needNum;
    }

    public boolean isStraight2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        Arrays.sort(nums);
        int zeroNum = 0;
        int last = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                zeroNum++;
            } else if(last != 0 && nums[i] == last) {
                //提前退出条件
                return false;
            }
            last = nums[i];
        }
        // 最大牌 - 最小牌 < length 则可构成顺子
        return nums[nums.length - 1] - nums[zeroNum] < nums.length;
    }
}
