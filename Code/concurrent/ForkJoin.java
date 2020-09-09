package com.todd.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author todd
 * @date 2020/6/8 22:00
 * @description: TODO
 */
public class ForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new AddTask(1, 5)));
    }
}

class AddTask extends RecursiveTask<Integer> {
    int begin;
    int end;
    public AddTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }
    @Override
    protected Integer compute() {
        // 5, 5
        if (begin == end) {
            System.out.println("join() " + begin);
            return begin;
        }
        // 4, 5
        if (end - begin == 1) {
            System.out.println("join() " + begin + " + " + end + " = " + (end + begin));
            return end + begin;
        }
        // 1 5
        int mid = (end + begin) / 2;
        AddTask t1 = new AddTask(begin, mid);
        t1.fork();
        AddTask t2 = new AddTask(mid + 1, end);
        t2.fork();
        System.out.println("fork() " + t1 + " + " + t2 + " = ?");
        int result = t1.join() + t2.join();
        System.out.println("fork() " + t1 + " + " + t2 + " = " + result);
        return result;
    }
}