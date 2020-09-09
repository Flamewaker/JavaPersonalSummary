package com.todd.exam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/28 9:11
 * @description: 编写一个在1,2，….9（顺序不能变）数字之间插入+或-或什么都不插入，使得计算结果总是100的程序。并输出所有的可能性。例如：1 + 2 + 34 – 5 + 67 – 8 + 9 = 100
 */
public class Tencent_Interview_8_28 {
    public static List<List<Integer>> ans = new ArrayList<>();

    public static void main(String[] args) {

        int[] nums = new int[]{1,2,3,4,5,6,7,8,9};
        ArrayList<Integer> cur = new ArrayList<>();
        findOneHundred(nums, 0, 0, cur);
        for (int i = 0; i < ans.size(); i++) {
            for (int j = 0; j < ans.get(i).size(); j++) {
                if (ans.get(i).get(j) < 0) {
                    System.out.print(ans.get(i).get(j));
                } else {
                    if (j != 0) {
                        System.out.print("+");
                    }
                    System.out.print(ans.get(i).get(j));
                }
            }
            System.out.println();
        }
    }



    public static void findOneHundred(int[] nums, int step, int sum, ArrayList<Integer> curr) {
        if (step >= nums.length) {
            if (sum == 100) {
                ans.add(new ArrayList(curr));
            }
            return;
        }

        curr.add(nums[step]);
        findOneHundred(nums, step + 1, sum + nums[step], curr);
        curr.remove(curr.size() - 1);

        if (step != 0) {
            curr.add((-1) * nums[step]);
            findOneHundred(nums, step + 1, sum - nums[step], curr);
            curr.remove(curr.size() - 1);
        }

        int num = 0;
        for (int j = step; j < nums.length; j++) {
            num = num * 10 + nums[j];
            curr.add(num);
            findOneHundred(nums, j + 1, sum + num, curr);
            curr.remove(curr.size() - 1);
            if (step != 0) {
                curr.add((-1) * num);
                findOneHundred(nums, j + 1, sum - num, curr);
                curr.remove(curr.size() - 1);
            }

        }
    }
}
