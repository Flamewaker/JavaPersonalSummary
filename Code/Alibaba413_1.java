package com.todd;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Auther todd
 * @Date 2020/4/13
 */
public class Alibaba413_1 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n = scanner.nextInt();
        int[] num = new int[n];
        for(int i = 0; i < n; i++){
            num[i] = scanner.nextInt();
        }
        int[] ans = new int[n];
        int[] visited = new int[n];
        for(int i = n - 1; i >= 0; i--){
            if(num[i] != 0 && visited[i] == 0){
                int cnt = 1;
                int father = i + 1;
                while(num[father - 1] != 0){
                    ans[father - 1] += cnt;
                    visited[father - 1] = 1;
                    father = num[father - 1];
                    if(visited[father - 1] == 0){
                        cnt++;
                    }
                }
                if(visited[father - 1] == 0){
                    ans[father - 1] += cnt;
                    visited[father - 1] = 1;
                } else {
                    ans[father - 1] += cnt;
                }

            } else if(num[i] == 0 && visited[i] == 0){
                ans[i]++;
            }
        }
        for(int i = 0; i < n; i++){
            System.out.println(ans[i]);
        }
    }
}
