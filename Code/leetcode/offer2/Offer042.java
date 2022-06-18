package com.todd.leetcode.offer2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tongchengdong
 * @description 剑指 Offer II 042. 最近请求次数
 * @date 4:55 PM 2022/6/17
 */
public class Offer042 {
    class RecentCounter {

        Queue<Integer> queue;

        public RecentCounter() {
            queue = new LinkedList<>();
        }

        public int ping(int t) {
            while(!queue.isEmpty() && queue.peek() < t - 3000){
                queue.poll();
            }
            queue.offer(t);
            return queue.size();
        }
    }
}
