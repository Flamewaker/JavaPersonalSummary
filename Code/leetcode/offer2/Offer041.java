package com.todd.leetcode.offer2;

import java.util.LinkedList;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 041. 滑动窗口的平均值
 * 整体思路：
 * 模拟双端队列
 * @date 4:14 PM 2022/6/17
 */
public class Offer041 {
    class MovingAverage {
        LinkedList<Integer> queue;
        int capacity;
        int count;
        int sum;

        /** Initialize your data structure here. */
        public MovingAverage(int size) {
            queue = new LinkedList<>();
            this.capacity = size;
            this.count = 0;
            this.sum = 0;
        }

        public double next(int val) {
            if (count >= capacity && !queue.isEmpty()) {
                int curr = queue.pollFirst();
                sum -= curr;
                count--;
            }
            queue.offerLast(val);
            sum += val;
            count++;
            return 1.0 * sum / count;
        }
    }
}
