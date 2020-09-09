package com.todd.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/7/15 13:31
 * @description: 你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]
 * 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？
 *
 * 判断图是否有环，利用图节点的出度来计算
 * 构造邻接表来存储图
 * 利用入度矩阵存储每个节点的入度
 * 将入度为0的节点一次入队，然后遍历，减少其对应端点的入度
 * 最后判断是否全部入队
 */
public class LeetCode207 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>(numCourses);
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new LinkedList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int thisCourse = prerequisites[i][0];
            int preCourse = prerequisites[i][1];
            indegrees[thisCourse]++;
            adjacency.get(preCourse).add(thisCourse);
        }
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int course = queue.poll();
            numCourses--;
            for (Integer nextCourse : adjacency.get(course)) {
                indegrees[nextCourse]--;
                if (indegrees[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        return numCourses == 0;
    }
}
