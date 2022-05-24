package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 207. 课程表
 * 整体思路：
 * 拓扑排序模板题
 * 判断图是否有环，利用图节点的出度来计算
 * 构造邻接表来存储图
 * 利用入度矩阵存储每个节点的入度
 * 将入度为0的节点一次入队，然后遍历，减少其对应端点的入度
 * 最后判断是否全部入队
 * @date 12:58 PM 2022/5/23
 */
public class LeetCode207 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses == 0) {
            return true;
        }
        int[] inDegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>(numCourses);
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new LinkedList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int thisCourse = prerequisites[i][0];
            int preCourse = prerequisites[i][1];
            inDegrees[thisCourse]++;
            adjacency.get(preCourse).add(thisCourse);
        }
        for (int i = 0; i < inDegrees.length; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer curr = queue.poll();
            numCourses--;
            List<Integer> nextCourse = adjacency.get(curr);
            for (Integer course : nextCourse) {
                inDegrees[course]--;
                if (inDegrees[course] == 0) {
                    queue.add(course);
                }
            }
        }
        return numCourses == 0;
    }
}
