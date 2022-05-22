package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tongchengdong
 * @description 49. 字母异位词分组
 * @date 12:17 AM 2022/5/22
 */
public class LeetCode49 {

    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList();
        }
        // 1. 对所有字符排序
        // 2. 构造String放入Hashmap中
        // 3. 将hashMap处理成List
        return new ArrayList(Arrays.stream(strs)
                .collect(Collectors.groupingBy(str -> {
                    // 返回 str 排序后的结果。
                    // 按排序后的结果来grouping by，算子类似于 sql 里的 group by。
                    char[] array = str.toCharArray();
                    Arrays.sort(array);
                    return new String(array);
                })).values());
    }

    public List<List<String>> groupAnagramsOptimized(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList();
        }
        // 1. 对所有字符排序
        // 2. 构造String放入Hashmap中
        // 3. 将hashMap处理成List
        return new ArrayList<>(Arrays.stream(strs)
                .collect(Collectors.groupingBy(str -> {
                    int[] counter = new int[26];
                    for (int i = 0; i < str.length(); i++) {
                        counter[str.charAt(i) - 'a']++;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 26; i++) {
                        // 这里的 if 是可省略的，但是加上 if 以后，生成的 sb 更短，后续 groupingBy 会更快。
                        if (counter[i] != 0) {
                            sb.append((char) ('a' + i));
                            sb.append(counter[i]);
                        }
                    }
                    return sb.toString();
                })).values());
    }
}
