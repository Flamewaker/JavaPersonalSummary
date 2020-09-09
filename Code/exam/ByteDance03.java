package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 11:26
 * @description: 机器人跳跃
 * 游戏中有N+1座建筑——从0到N编号，从左到右排列。编号为0的建筑高度为0个单位，编号为i的建筑的高度为H(i)个单位。
 * 起初， 机器人在编号为0的建筑处。每一步，它跳到下一个（右边）建筑。假设机器人在第k个建筑，且它现在的能量值是E, 下一步它将跳到第个k+1建筑。它将会得到或者失去正比于与H(k+1)与E之差的能量。
 * 如果 H(k+1) > E 那么机器人就失去 H(k+1) - E 的能量值，否则它将得到 E - H(k+1) 的能量值。
 *
 * 游戏目标是到达第个N建筑，在这个过程中，能量值不能为负数个单位。现在的问题是机器人以多少能量值开始游戏，才可以保证成功完成游戏？
 * nums[i] > E => 2E - nums[i]
 * nums[i] <= E => 2E - nums[i]
 *
 * 2^n E = 2^(n -1) * nums[1] + ... + 2 ^ 0 * nums[n]
 * E_0 = 1 / 2 * nums[1] + ... + 1 / 2^n * nums[n]
 * 从后往前逆推，每次需要向上取整。
 */
public class ByteDance03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            int E = 0;
            for (int i = nums.length - 1; i >= 0; i--) {
                E = (int) Math.ceil((E + nums[i]) / 2.0);
//                E = (E + nums[i] + 1) >> 1;
            }
            System.out.println(E);
        }
    }
}
