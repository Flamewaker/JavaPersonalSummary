package com.todd.redo.offer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/20 16:27
 * @description: TODO
 */
public class Offer57_2 {
    public int[][] findContinuousSequence(int target) {
        List<int[]> ans = new ArrayList<>();
        int left = 1;
        int right = 1;
        int sum = 0;
        while (left <= target / 2) {
            if (sum < target) {
                sum += right;
                right++;
            } else if (sum > target) {
                sum -= left;
                left++;
            } else {
                ans.add(new int[]{left, right});
                sum -= left;
                left++;
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
