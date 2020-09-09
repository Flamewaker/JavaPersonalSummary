package com.todd.code;

import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        char[][] feild = new char[6][6];
        for (int i = 0; i < 6; i++) {
            feild[i] = in.nextLine().toCharArray();
        }
        in.nextLine().toCharArray();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String string = sc.next();
        }

        Scanner sc2 = new Scanner(System.in);
        int n2 = sc2.nextInt();
        int[] arr = new int[n2];
        for (int i = 0; i < n2; i++) {
            arr[i] = sc2.nextInt();
        }

        String str = sc.nextLine();

        Scanner sc3 = new Scanner(System.in);
        int n3 = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        int a[] = new int[n3];

        int ans = 0;

        PriorityQueue<int[]> que1 = new PriorityQueue<int[]>((a1,a2)->{
            if(a1[0]!=a2[0]){
                return a2[0]-a1[0];
            }
            else{
                return a1[1]-a2[1];
            }
        });

        PriorityQueue<Integer> heap = new PriorityQueue<>((a12, b) -> b - a12);

        Integer[] arr2 = new Integer[20];
        Arrays.sort(arr2, (o1, o2) -> o1 - o2);

        Arrays.sort(arr2, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        double res = 0.0;
        DecimalFormat df = new DecimalFormat("#0.000000");
        System.out.println(df.format(res));

        List<Integer> c = new LinkedList<Integer>();
        System.out.println(String.format("%.1f", res));
        System.out.printf("%.1f\n", res);
    }
}
