package com.todd.exam;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/8/24 14:11
 * @description:
 * 魔塔是一款时尚经典小游戏，我们将魔塔简化后的规则描述如下：
 * 魔塔有n关，而你可以自由选择前往攻略哪一关，每一关只能获得一次分数。第i关攻略完成后，你将会获得ai的分数。某些关有一个特殊的宝物，你只能在攻略完这一关的时候使用这个宝物（也可以不使用，额外的宝物并不能留到其他关卡使用），这个宝物将使得这一关不得分，但是将你现有的总得分乘以2作为新的得分。
 * 你现在知道了所有关卡的通关方法，也知道了每一关的得分和是否有宝物，你现在想知道，怎么选择攻略的顺序和使用宝物的方法才能让自己的得分最大化？
 */
public class T360_8_24_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] nums = new int[n][2];
        if (n <= 100 && n > 0) {
            for (int i = 0; i < n; i++) {
                nums[i][0] = sc.nextInt();
                nums[i][1] = sc.nextInt();
            }
            System.out.println(solve(nums, n));
        }
    }

    public static int solve(int[][] nums, int n) {
        PriorityQueue<Integer> que0 = new PriorityQueue<>((o1, o2) -> o2 - o1);
        PriorityQueue<Integer> que1 = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int sum0 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i][1] == 1) {
                que1.add(nums[i][0]);
            } else {
                que0.add(nums[i][0]);
            }
        }
        int count = 0;

        while (!que0.isEmpty()) {
            count += que0.poll();
        }

        while (!que1.isEmpty()) {
            if (count > que1.peek()) {
                count *= 2;
            } else {
                count += que1.peek();
            }
            que1.poll();
        }
        return count;
     }
}
