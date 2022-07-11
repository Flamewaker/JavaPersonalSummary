package com.todd.nowcoder;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author tongchengdong
 * @description 合并表记录
 * @date 9:28 PM 2022/7/2
 */
public class HJ8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int next = sc.nextInt();
            TreeMap<Integer, Integer> map = new TreeMap<>();
            for (int i = 0; i < next; i++) {
                int key = sc.nextInt();
                int value = sc.nextInt();
                map.put(key, map.getOrDefault(key, 0) + value);
            }
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
