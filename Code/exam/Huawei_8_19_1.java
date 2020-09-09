package com.todd.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Huawei_8_19_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        if (m <= 0 || n <= 0) {
            StringBuilder str = new StringBuilder();
            str.append("[");
            str.append("]");
            System.out.println(str.toString());
        } else {
            boolean[][] visited = new boolean[m][n];
            int sum = m * n;
            int state = 0;
            int count = 0;
            int x = 0, y = 0;
            ArrayList<int[]> ans = new ArrayList<>();
            for (int i = 0; i < sum; i++) {
                visited[x][y] = true;
                count++;

                int temp = count;
                int a = temp % 10;
                int b = temp / 10 % 10;
                if (a == 7 && (b & 1) == 1) {
                    ans.add(new int[]{x, y});
                }
                if (state == 0) {
                    if (y + 1 == n || visited[x][y + 1]) {
                        x++;
                        state = 1;
                    } else {
                        y++;
                    }
                } else if (state == 1) {
                    if (x + 1 == m || visited[x + 1][y]) {
                        y--;
                        state = 2;
                    } else {
                        x++;
                    }
                } else if (state == 2) {
                    if (y == 0 || visited[x][y - 1]) {
                        x--;
                        state = 3;
                    } else {
                        y--;
                    }
                } else if (state == 3) {
                    if (x == 0 || visited[x - 1][y]) {
                        y++;
                        state = 0;
                    } else {
                        x--;
                    }
                }
            }
            StringBuilder str = new StringBuilder();
            str.append("[");
            for (int i = 0; i < ans.size(); i++) {
                str.append("[" + ans.get(i)[0] + "," + ans.get(i)[1] + "]");
                if (i != ans.size() - 1) {
                    str.append(",");
                }
            }
            str.append("]");
            System.out.println(str.toString());
        }

    }
}