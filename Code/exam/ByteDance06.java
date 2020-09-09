package com.todd.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 13:30
 * @description: 总共有36张牌，每张牌是1~9。每个数字4张牌。
 * 你手里有其中的14张牌，如果这14张牌满足如下条件，即算作和牌
 * 1. 14张牌中有2张相同数字的牌，称为雀头。
 * 2. 除去上述2张牌，剩下12张牌可以组成4个顺子或刻子。顺子的意思是递增的连续3个数字牌（例如234,567等），刻子的意思是相同数字的3个数字牌（例如111,777）
 *
 * 回溯算法，简单。
 */
public class ByteDance06 {

    public static int[] count = new int[10];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            count = new int[10];
            for (int i = 0; i < 13; i++) {
                int num = sc.nextInt();
                count[num]++;
            }
            ArrayList<Integer> ans = solve();
            if (ans.size() != 0) {
                for (int i = 0; i < ans.size(); i++) {
                    if (i == ans.size() - 1) {
                        System.out.println(ans.get(i));
                    } else {
                        System.out.print(ans.get(i) + " ");
                    }
                }
            } else {
                System.out.println(0);
            }

        }
    }

    public static ArrayList<Integer> solve() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (count[i] == 4) {
                continue;
            }
            count[i]++;
            for (int j = 1; j <= 9; j++) {
                if (count[j] >= 2) {
                    count[j] -= 2;
                    boolean flag = win(12);
                    count[j] += 2;
                    if (flag) {
                        ans.add(i);
                        break;
                    }
                }
            }
            count[i]--;
        }
        return ans;
    }

    public static boolean win(int num) {
        if (num == 0) {
            return true;
        }

        for (int i = 1; i <= 9; i++) {
            if (count[i] >= 3) {
                count[i] -= 3;
                boolean flag = win(num - 3);
                count[i] += 3;
                if (flag) {
                    return true;
                }
            }
        }

        for (int i = 1; i <= 7; i++) {
            if (count[i] >= 1 && count[i + 1] >= 1 && count[i + 2] >= 1) {
                count[i]--;
                count[i + 1]--;
                count[i + 2]--;
                boolean flag = win(num - 3);
                count[i]++;
                count[i + 1]++;
                count[i + 2]++;
                if (flag) {
                    return true;
                }
            }
        }

        return false;
    }


}
