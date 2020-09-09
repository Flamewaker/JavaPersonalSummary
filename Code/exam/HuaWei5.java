package com.todd.exam;

import java.util.*;

/**
 * @author todd
 * @date 2020/7/22 19:02
 * @description: 字符统计
 * 如果统计的个数相同，则按照ASCII码由小到大排序输出。如果有其他字符，则对这些字符不用进行统计。
 */
public class HuaWei5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < str.length(); i++) {
                char a = str.charAt(i);
                map.put(a, map.getOrDefault(a, 0) + 1);
            }
            ArrayList<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
            list.sort(new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                    if (o1.getValue().equals(o2.getValue())) {
                        return o1.getKey().compareTo(o2.getKey());
                    } else {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                }
            });
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    System.out.println(list.get(i).getKey());
                } else {
                    System.out.print(list.get(i).getKey());
                }
            }
        }

    }
}
