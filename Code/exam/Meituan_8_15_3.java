package com.todd.exam;

import java.util.*;

/**
 * @author todd
 * @date 2020/8/15 15:47
 * @description: TODO
 */
public class Meituan_8_15_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] pre = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = i;
        }
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            merge(x, y, pre);
        }
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            if (map.containsKey(findFather(i, pre))) {
                map.get(findFather(i, pre)).add(i);
            } else {
                ArrayList<Integer> temp = new ArrayList<>();
                map.put(findFather(i, pre), temp);
                map.get(findFather(i, pre)).add(i);
            }
        }

        PriorityQueue<ArrayList<Integer>> pq = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        });

        for (ArrayList value : map.values()) {
            Collections.sort(value);
            pq.add(value);
        }
        ArrayList<Integer> ans2 = new ArrayList<>();
        System.out.println(pq.size());
        while (!pq.isEmpty()) {
            ArrayList<Integer> ans = pq.poll();
            ans2.addAll(ans);
        }
        for (int i = 0; i < ans2.size(); i++) {
            if (i == ans2.size() - 1) {
                System.out.println(ans2.get(i));
            } else {
                System.out.print(ans2.get(i) + " ");
            }
        }
    }

    public static int findFather(int a, int[] pre) {
        if (a == pre[a]) {
            return a;
        } else {
            return findFather(pre[a], pre);
        }
    }

    public static void merge(int a, int b, int[] pre) {
        if (a < b) {
            int temp = a;
            a = b;
            b = temp;
        }
        int ax = findFather(a, pre);
        int bx = findFather(b, pre);
        if (ax != bx) {
            pre[ax] = bx;
        }
    }
}
