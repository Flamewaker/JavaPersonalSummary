package com.todd.code;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author todd
 * @date 2020/7/24 10:44
 * @description: 179. 最大数
 * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
 * 比较 ab 与 ba的大小，按降序排列；
 */
public class LeetCode179 {
    public String largestNumber(int[] nums) {
        if (nums.length == 0) {
            return "";
        }
        int length = nums.length;
        StringBuilder num = new StringBuilder();
        String[] numStr = new String[length];
        for (int i = 0; i < length; i++) {
            numStr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(numStr, new Comparator<String>(){
            @Override
            public int compare(String a, String b) {
                String order1 = a + b;
                String order2 = b + a;
                return order2.compareTo(order1);
            }
        });
        //Arrays.sort(numStr, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));
        for (int i = 0; i < length; i++) {
            num.append(numStr[i]);
        }

         //排除多个0的情况[0,0]

        if (num.charAt(0) == '0') {
            return "0";
        }

        return num.toString();
    }
}
