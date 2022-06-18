package com.todd.leetcode.offer2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 030. 插入、删除和随机访问都是 O(1) 的容器
 * 整体思路：
 * 数组 + 哈希表
 * @date 12:24 AM 2022/6/17
 */
public class Offer030 {
    class RandomizedSet {
        int[] nums;
        Random random;
        Map<Integer, Integer> map;
        int idx;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            nums = new int[20010];
            random = new Random();
            map = new HashMap<>();
            idx = -1;
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            nums[++idx] = val;
            map.put(val, idx);
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            Integer index = map.get(val);
            if (index != idx) {
                map.put(nums[idx], index);
            }
            nums[index] = nums[idx--];
            map.remove(val);
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return nums[random.nextInt(idx + 1)];
        }
    }
}
