package com.todd.code;

import java.util.LinkedHashMap;

/**
 * @author todd
 * @date 2020/5/19 10:41
 * @description: LeetCode 146 面试题 16.25. LRU缓存 重点题目
 * HashMap是无序的，当我们希望有顺序地去存储key-value时，就需要使用LinkedHashMap了。
 *
 */
public class LRUCacheLinkedHashMap {
    int capacity;
    LinkedHashMap<Integer, Integer> hashMap;

    public LRUCacheLinkedHashMap(int capacity) {
        this.capacity = capacity;
        hashMap = new LinkedHashMap<>();
    }

    public int get(int key) {
        if (!hashMap.containsKey(key)) {
            return -1;
        }

        int value = hashMap.get(key);
        hashMap.remove(key);
        hashMap.put(key, value);
        return hashMap.get(key);
    }


    public void put(int key, int value) {
        if (hashMap.containsKey(key)) {
            hashMap.remove(key);
            hashMap.put(key, value);
            return;
        }
        //超出capacity，删除最久没用的,利用迭代器，删除第一个
        if (hashMap.size() >= capacity) {
            hashMap.remove(hashMap.keySet().iterator().next());
        }
        hashMap.put(key, value);
    }

}
