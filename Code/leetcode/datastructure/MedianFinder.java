package com.todd.leetcode.datastructure;

import java.util.PriorityQueue;

/**
 * @author todd
 * @date 2020/6/16 22:49
 * @description: LeetCode295 数据流的中位数
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 *
 * 采用两个heap，一个maxheap和一个minheap来进行处理，在heap中插入num的时间复杂度是O（logn），所以快捷了很多。
 * 为了找到添加新数据以后，数据流的中位数，我们让这个新数据在大顶堆和小顶堆中都走了一遍。而为了让大顶堆的元素多 11 个，我们让从小顶堆中又拿出一个元素“送回”给大顶堆；
 *
 */
public class MedianFinder {
    private int count = 0;
    PriorityQueue<Integer> min ;
    PriorityQueue<Integer> max ;
    /** initialize your data structure here. */
    public MedianFinder() {
        count = 0;
        min = new PriorityQueue<>((a,b) -> a - b);
        max = new PriorityQueue<>((a,b) -> b - a);
    }

    public void addNum(int num) {
        count++;
        if (max.size() == 0 && num < max.peek()) {
            max.add(num);
        } else {
            min.add(num);
        }
        if (max.size() > min.size() + 1) {
            min.add(max.poll());
        } else if (max.size() < min.size()) {
            max.add(min.poll());
        }
    }

    public double findMedian() {
        if ((count & 1) == 0) {
            return (max.peek() + min.peek()) / 2.0;
        }
        else {
            return max.peek();
        }
    }

    public void addNum2(int num) {
        count++;
        min.add(num);
        max.add(min.poll());
        if ((count & 1) == 0) {
            min.add(max.poll());
        }
    }
}
